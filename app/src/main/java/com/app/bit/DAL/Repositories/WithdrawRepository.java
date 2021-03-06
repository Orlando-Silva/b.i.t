package com.app.bit.DAL.Repositories;

import android.app.Application;

import com.app.bit.DAL.BitRoomDatabase;
import com.app.bit.DAL.DAO.WithdrawDao;
import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Entities.Withdraw;
import com.app.bit.DAL.HttpRequestObjects.Input;
import com.app.bit.DAL.HttpRequestObjects.Output;
import com.app.bit.DAL.HttpRequestObjects.SendWithdrawFirstStepRequest;
import com.app.bit.DAL.HttpRequestObjects.SignerRequest;
import com.app.bit.DAL.HttpResponseObjects.BlockcypherTransactionResponse;
import com.app.bit.DAL.HttpResponseObjects.Deposit.TransactionsByAddress;
import com.app.bit.DAL.HttpResponseObjects.SignerResponse;
import com.app.bit.DAL.HttpResponseObjects.WithdrawResponse.SendWithdrawFirstStepResponse;
import com.app.bit.Helpers.HttpHelpers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WithdrawRepository {

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

    public Withdraw makeWithdraw(int userId, double amount,String to, double currentUserBalance) throws Exception {

        SendWithdrawFirstStepRequest withdrawFirstStep = generateFirstStepObject(userId, amount, to);
        SendWithdrawFirstStepResponse response = requestWithdrawFirstStep(withdrawFirstStep);

        double withdrawTotal = 0;
        double fee = 0;

        for (com.app.bit.DAL.HttpResponseObjects.WithdrawResponse.Output output: response.getTx().getOutputs()) {
            withdrawTotal += output.getValue() / Double.parseDouble(100000000 + "");
        }

        fee = response.getTx().getFees() / Double.parseDouble(100000000 + "");
        withdrawTotal +=  fee;

        if(withdrawTotal > currentUserBalance) {
            DecimalFormat df = new DecimalFormat("#.########");
            throw new WithdrawException("Para fazer a retirada de " + df.format(amount) + " BTC é necessário ter, em conta " + df.format(withdrawTotal - amount) + " BTC por causa da taxa de rede!");
        }


        List<String> toSignData = new ArrayList<String>();
        List<String> pubkeys = new ArrayList<String>();

        int i = 0;

        for (com.blockcypher.model.transaction.input.Input input :response.getTx().getInputs()) {


            Address fromAddress = mAddressRepository.getByPublicAddress(input.getAddresses().get(0));

            pubkeys.add(fromAddress.getPublicKey());

            SignerRequest signerRequest = new SignerRequest();
            signerRequest.setKey(fromAddress.getPrivateKey());
            signerRequest.setData(response.getTosign().get(i));

            SignerResponse signerResponse = signTransaction(signerRequest);

            if(signerResponse == null || signerResponse.getStatusCode() != 200)
                throw new Exception(signerResponse.getBody());

            String signData = signerResponse.getBody().replace("{\"response\":\"","").replace("\"}", "");
            toSignData.add(signData);
            i++;
        }


        response.setSignatures(toSignData);
        response.setPubkeys(pubkeys);
        response = requestWithdrawSecondStep(response);

        if(response.getErrors() == null && response.getTx() != null) {

            Withdraw withdraw = new Withdraw();
            withdraw.setAmount(withdrawTotal);
            withdraw.setTo(to);
            withdraw.setConfirmations(response.getTx().getConfirmations());
            withdraw.setTxId(response.getTx().getHash());
            withdraw.setUserId(userId);
            withdraw.setCreatedAt(new Date());
            withdraw.setFee(fee);

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
        return HttpHelpers.makeSecondStepWithdrawPostRequest("https://api.blockcypher.com/v1/bcy/test/txs/send",
                withdrawFirstStepResponse,
                new SendWithdrawFirstStepResponse().getClass());
    }

    public SendWithdrawFirstStepResponse requestWithdrawFirstStep(SendWithdrawFirstStepRequest withdrawFirstStepRequest) throws Exception {
              return HttpHelpers.makeWithdrawPostRequest("https://api.blockcypher.com/v1/bcy/test/txs/new",
                      withdrawFirstStepRequest,
                      new SendWithdrawFirstStepResponse().getClass());
    }

    public BlockcypherTransactionResponse getTransaction(String txId) {

        return HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/bcy/test/txs/" + txId + "?limit=5000&includeHex=false",
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
        TransactionsByAddress rawAddress =  mAddressRepository.getAddressInBlockchain(address.getPublicAddress());

        if(rawAddress == null){
            return 0;
        }
        else {
            return rawAddress.getBalance() / Double.parseDouble(100000000 + "");
        }
    }

    public class  WithdrawException extends Exception {

        public WithdrawException(String message) {
            super(message);
        }
    }

}
