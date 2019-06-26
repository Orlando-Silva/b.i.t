package com.example.bit.DAL.Repositories;

import android.app.Application;
import android.util.Log;

import com.blockcypher.model.transaction.intermediary.IntermediaryTransaction;
import com.blockcypher.utils.sign.SignUtils;
import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.WithdrawDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Entities.Withdraw;
import com.example.bit.DAL.HttpRequestObjects.Input;
import com.example.bit.DAL.HttpRequestObjects.Output;
import com.example.bit.DAL.HttpRequestObjects.SendWithdrawFirstStepRequest;
import com.example.bit.DAL.HttpRequestObjects.SignerRequest;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawAddressResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockcypherTransactionResponse;
import com.example.bit.DAL.HttpResponseObjects.SignerResponse;
import com.example.bit.DAL.HttpResponseObjects.WithdrawResponse.SendWithdrawFirstStepResponse;
import com.example.bit.Helpers.HttpHelpers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WithdrawRepository {

    public final double WITHDRAW_FEE = 0.0004;
    private WithdrawDao mWithdrawDao;
    private AddressRepository mAddressRepository;

    public WithdrawRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mWithdrawDao = db.withdrawDao();
        mAddressRepository = new AddressRepository(application);
    }

    public void insert(Withdraw withdraw) { mWithdrawDao.insert(withdraw); }

    public void delete(int id) { mWithdrawDao.delete(id); }

    public void deleteAll() { mWithdrawDao.deleteAll(); }

    public Withdraw get(int id) { return mWithdrawDao.get(id); }

    public List<Withdraw> getAll() { return mWithdrawDao.getAll(); }

    public List<Withdraw> getAllByUser(int userId) { return mWithdrawDao.getAllByUser(userId); }

    public LiveData<List<Withdraw>> getAllLiveDataByUser(int userId) { return mWithdrawDao.getAllLiveDataByUser(userId); }

    public List<Withdraw> getByTxId(String txId) { return mWithdrawDao.getByTxId(txId); }

    public List<Withdraw> getUnconfirmedWithdraws() { return mWithdrawDao.getUnconfirmedWithdraws(); }

    public void update(Withdraw withdraw) { mWithdrawDao.update(withdraw); }

    public Withdraw makeWithdraw(int userId, double amount,String to) throws Exception {

        SendWithdrawFirstStepRequest withdrawFirstStep = generateFirstStepObject(userId, amount, to);
        SendWithdrawFirstStepResponse response = requestWithdrawFirstStep(withdrawFirstStep);

        Address fromAddress = mAddressRepository.getByPublicAddress(withdrawFirstStep.getInputs().get(0).getAddresses().get(0));

        List<String> pubkeys = new ArrayList<String>();
        pubkeys.add(fromAddress.getPublicKey());

        SignerRequest signerRequest = new SignerRequest();
        signerRequest.setKey(fromAddress.getPrivateKey());
        signerRequest.setData(response.getTosign().get(0));

        SignerResponse signerResponse = signTransaction(signerRequest);

        if(signerResponse == null || signerResponse.getStatusCode() != 200)
            throw new Exception(signerResponse.getBody());

        String signData = signerResponse.getBody().replace("{\\\"response\\\":\\\"","").replace("\\\"}", "");
        List<String> toSignData = new ArrayList<String>();
        toSignData.add(signData);

        response.setTosign(toSignData);
        response.setPubkeys(pubkeys);
        response = requestWithdrawSecondStep(response);

        if(response.getErrors() == null) {

            Withdraw withdraw = new Withdraw();
            withdraw.setAmount(amount);
            withdraw.setTo(to);
            withdraw.setConfirmations(response.getTx().getConfirmations());
            withdraw.setTxId(response.getTx().getHash());
            withdraw.setUserId(userId);
            withdraw.setCreatedAt(new Date());
            withdraw.setFee(WITHDRAW_FEE);

            insert(withdraw);

            return withdraw;
        }
        else {
            throw new Exception(response.getErrors().get(0).getError());
        }
    }

    public SignerResponse signTransaction(SignerRequest withdrawSignerRequest) throws Exception {
        return HttpHelpers.makeSignWithdrawPostRequest("https://cktn9ywch5.execute-api.sa-east-1.amazonaws.com/signer-api",
                withdrawSignerRequest,
                new SignerResponse().getClass());
    }

    public SendWithdrawFirstStepResponse requestWithdrawSecondStep(SendWithdrawFirstStepResponse withdrawFirstStepResponse) throws Exception {
        return HttpHelpers.makeSecondStepWithdrawPostRequest("https://api.blockcypher.com/v1/btc/test3/txs/send",
                withdrawFirstStepResponse,
                new SendWithdrawFirstStepResponse().getClass());
    }

    public SendWithdrawFirstStepResponse requestWithdrawFirstStep(SendWithdrawFirstStepRequest withdrawFirstStepRequest) throws Exception {
              return HttpHelpers.makeWithdrawPostRequest("https://api.blockcypher.com/v1/btc/test3/txs/new",
                      withdrawFirstStepRequest,
                      new SendWithdrawFirstStepResponse().getClass());
    }

    public BlockcypherTransactionResponse getTransaction(String txId) {

        return HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/btc/test3/txs/" + txId + "?limit=5000&includeHex=false",
                new BlockcypherTransactionResponse().getClass());
    }

    public void verifyPendingWithdraws(Withdraw withdraw) {

        BlockcypherTransactionResponse response = getTransaction(withdraw.getTxId());

        if(response == null)
            return;
        
        if(response.getConfirmations() > withdraw.getConfirmations()) {
            withdraw.setConfirmations(response.getConfirmations());
            mWithdrawDao.update(withdraw);
        }

    }
    
    private SendWithdrawFirstStepRequest generateFirstStepObject(int userId, double amount, String to) throws Exception {
        SendWithdrawFirstStepRequest withdrawFirstStep = new SendWithdrawFirstStepRequest();
        withdrawFirstStep.setInputs(setInputs(userId, amount));
        withdrawFirstStep.setOutputs(generateOutputs(to, amount));
        return  withdrawFirstStep;
    }

    private List<Output> generateOutputs(String destinationAddress, double  amount) {
        List<Output> outputs = new ArrayList<>();
        Output output = new Output();
        List<String> addresses  = new ArrayList<>();
        addresses.add(destinationAddress);
        output.setAddresses(addresses);
        Double temporaryValue = (amount * 100000000);

        output.setValue(temporaryValue.intValue());
        outputs.add(output);
        return outputs;
    }


    private List<Input> setInputs(int userId, double amount) throws Exception {
        List<Input> inputs = generateInputs(userId, amount);

        if(inputs.isEmpty())
            throw new Exception("Não foi possível gerar os inputs.");

        return inputs;
    }

    private List<Input> generateInputs(int userId, double amount) {
        List<Address> userAdresses = mAddressRepository.getAllByUser(userId);

        double addressesBalance = 0;
        List<Input> inputs = new ArrayList<Input>();

        for (Address address: userAdresses) {

            if(surpassTheRequiredAmount(addressesBalance, amount))
                break;

            double addressBalance = getAddressBalance(address);

            if(addressBalance > 0) {
                addressesBalance += addressBalance;
                inputs.add(generateInput(address));
            }
        }

        return inputs;
    }

    private Input generateInput(Address address) {
        List<String> addresses  = new ArrayList<>();
        addresses.add(address.getPublicAddress());

        Input input = new Input();
        input.setAddresses(addresses);
        input.setScript_type("pay-to-pubkey-hash");
        return input;
    }

    private boolean surpassTheRequiredAmount(double currentAmount, double neededAmount) {
        return currentAmount >= neededAmount;
    }

    private double getAddressBalance(Address address) {
        BlockchainRawAddressResponse rawAddress =  mAddressRepository.getAddressInBlockchain(address.getPublicAddress());

        if(rawAddress == null){
            return 0;
        }
        else {
            return rawAddress.getFinalBalance() / Double.parseDouble(100000000 + "");
        }
    }
}
