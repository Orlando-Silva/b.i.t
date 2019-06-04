package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.AddressDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Enums.Status;
import com.example.bit.DAL.HttpResponseObjects.BlockcypherCreateAddressResponse;
import com.example.bit.Helpers.HttpHelpers;

import java.util.List;

public class AddressRepository {

    private AddressDao mAddressDao;

    public AddressRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mAddressDao = db.addressDao();
    }

    public void insert(Address address) { mAddressDao.insert(address); }

    public void delete(int id) { mAddressDao.delete(id); }

    public void deleteAll() { mAddressDao.deleteAll(); }

    public Address get(int id) { return mAddressDao.get(id); }

    public List<Address> getAllByUser(int userId) { return mAddressDao.getAllByUser(userId);}

    public boolean userHasAddress(int userId) {

        List<Address> addresses = getAllByUser(userId);

        return addresses != null && !addresses.isEmpty();
    }

    public Address generateFirstAddress(int userId) throws Exception {
        if(!userHasAddress(userId)) {
            return generateAddress(userId);
        }
        else {
            throw new Exception("Usuário já tem um endereço");
        }
    }

    public Address generateAddress(int userId) throws Exception {
        BlockcypherCreateAddressResponse response =  HttpHelpers.makePostRequest(" https://api.blockcypher.com/v1/btc/test3/addrs",
                null, new BlockcypherCreateAddressResponse().getClass());

        if(response == null) {
            throw new Exception("Erro de conexão, não foi possível criar um endereço novo");
        } else {
            Address address = new Address();
            address.setPrivateKey(response.get_private());
            address.setPublicAddress(response.get_address());
            address.setPublicKey(response.get_public());
            address.setStatus(Status.ACTIVE);
            address.setUserId(userId);
            mAddressDao.insert(address);
            return address;
        }

    }

    public Address addLabelToAddress(int addressId, String label) {
        mAddressDao.addLabelToAddress(addressId, label);
        return mAddressDao.get(addressId);
    }


}
