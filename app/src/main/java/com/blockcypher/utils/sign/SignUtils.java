package com.blockcypher.utils.sign;

import com.blockcypher.model.transaction.intermediary.IntermediaryTransaction;
import com.core.Base58;

import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequenceGenerator;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Util to sign a transaction
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class SignUtils {


    public static void signWithHexKeyNoPubKey(IntermediaryTransaction unsignedTransaction, String hexKey) {
        sign(unsignedTransaction, hexKey, true, false);
    }

    public static void signWithHexKeyWithPubKey(IntermediaryTransaction unsignedTransaction, String hexKey) {
        sign(unsignedTransaction, hexKey, true, true);
    }

    public static void signWithBase58KeyWithPubKey(IntermediaryTransaction unsignedTransaction, String privateKey) {
        sign(unsignedTransaction, privateKey, false, true);
    }

    private static void sign(IntermediaryTransaction unsignedTransaction, String privateKey, boolean isHex, boolean addPubKey) {
        byte[] bytes;
        boolean compressed = false;
        if (isHex) {
            // nothing to do
            bytes = Hex.decode(privateKey);
        } else {
            bytes = getBytesFromBase58Key(privateKey);
        }
        if (bytes.length == 33 && bytes[32] == 1) {
            compressed = true;
            bytes = Arrays.copyOf(bytes, 32);  // Chop off the additional marker byte.
        }
        BigInteger privKeyB = new BigInteger(1, bytes);

        X9ECParameters params = SECNamedCurves.getByName("secp256k1");
        ECDomainParameters CURVE = new ECDomainParameters(params.getCurve(), params.getG(), params.getN(), params.getH());
        BigInteger HALF_CURVE_ORDER = params.getN().shiftRight(1);
        SecureRandom secureRandom = new SecureRandom();

        ECPoint point = CURVE.getG().multiply(privKeyB);
        if (compressed) {
            point = new ECPoint.Fp(CURVE.getCurve(), point.getX(), point.getY(), true);
        }

        byte[] publicKey = point.getEncoded();

        ECDSASigner signer = new ECDSASigner();
        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privKeyB, CURVE);
        signer.init(true, privKey);

        for (int i = 0; i < unsignedTransaction.getTosign().size(); i++) {
            String toSign = unsignedTransaction.getTosign().get(i);
            if (addPubKey) {
                unsignedTransaction.addPubKeys(bytesToHexString(publicKey));
            }
            BigInteger[] components = signer.generateSignature(Hex.decode(toSign));
            BigInteger r = components[0];
            BigInteger s = components[1];
            // ensure Canonical
            s = ensureCanonical(s, HALF_CURVE_ORDER, CURVE);
            String signedString = bytesToHexString(toDER(r, s));
            unsignedTransaction.addSignature(signedString);
        }
    }

    private static byte[] getBytesFromBase58Key(String privateKey) {
        byte[] tmp = new byte[0];
        try {
            tmp = Base58.decodeChecked(privateKey);
        } catch (Exception e) {

        }
        int version = tmp[0] & 0xFF;
        byte[] bytes = new byte[tmp.length - 1];
        System.arraycopy(tmp, 1, bytes, 0, tmp.length - 1);
        return bytes;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (byte b : bytes) {
            String sT = Integer.toString(0xFF & b, 16);
            if (sT.length() < 2)
                buf.append('0');
            buf.append(sT);
        }
        return buf.toString();
    }

    private static byte[] toDER(BigInteger r, BigInteger s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(72);
        DERSequenceGenerator seq = null;
        byte[] res = new byte[0];
        try {
            seq = new DERSequenceGenerator(bos);
            seq.addObject(new DERInteger(r));
            seq.addObject(new DERInteger(s));
            seq.close();
            res = bos.toByteArray();
            return res;
        } catch (IOException e) {
        }
        return null;
    }

    private static BigInteger ensureCanonical(BigInteger s, BigInteger HALF_CURVE_ORDER, ECDomainParameters CURVE) {
        if (s.compareTo(HALF_CURVE_ORDER) > 0) {
            // The order of the curve is the number of valid points that exist on that curve. If S is in the upper
            // half of the number of valid points, then bring it back to the lower half. Otherwise, imagine that
            //    N = 10
            //    s = 8, so (-8 % 10 == 2) thus both (r, 8) and (r, 2) are valid solutions.
            //    10 - 8 == 2, giving us always the latter solution, which is canonical.
            s = CURVE.getN().subtract(s);
        }
        return s;
    }

}
