package com.example.bit.DAL.Repositories;

import android.app.Application;
import android.util.Log;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.WithdrawDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Withdraw;
import com.example.bit.DAL.HttpRequestObjects.Input;
import com.example.bit.DAL.HttpRequestObjects.Output;
import com.example.bit.DAL.HttpRequestObjects.SendWithdrawFirstStepRequest;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawAddressResponse;
import com.example.bit.DAL.HttpResponseObjects.WithdrawResponse.SendWithdrawFirstStepResponse;
import com.example.bit.Helpers.HttpHelpers;

import java.math.BigInteger;
import java.util.ArrayList;
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

    public List<Withdraw> getByAddress(int addressId) { return mWithdrawDao.getByAddress(addressId); }

    public List<Withdraw> getByTxIdAndAddress(String txId, String address) { return mWithdrawDao.getByTxIdAndAddress(txId, address); }

    public List<Withdraw> getUnconfirmedWithdraws() { return mWithdrawDao.getUnconfirmedWithdraws(); }

    public void update(Withdraw withdraw) { mWithdrawDao.update(withdraw); }

    public Withdraw makeWithdraw(int userId, double amount,String to) throws Exception {

        SendWithdrawFirstStepRequest withdrawFirstStep = generateFirstStepObject(userId, amount, to);
        SendWithdrawFirstStepResponse response = requestWithdrawFirstStep(withdrawFirstStep);
        Log.d("Observe", response.toString());
        return null;
    }

    public SendWithdrawFirstStepResponse requestWithdrawFirstStep(final SendWithdrawFirstStepRequest withdrawFirstStepRequest) throws InterruptedException {
              return HttpHelpers.makeWithdrawPostRequest("https://api.blockcypher.com/v1/btc/test3/txs/new",
                      withdrawFirstStepRequest,
                      new SendWithdrawFirstStepResponse().getClass());
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
