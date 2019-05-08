package org.hyperledger.fabric.example;

import java.util.List;

import com.google.protobuf.ByteString;
import io.netty.handler.ssl.OpenSsl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SimpleChaincode extends ChaincodeBase {

    private static Log _logger = LogFactory.getLog(SimpleChaincode.class);

    @Override
    public Response init(ChaincodeStub stub) {
        List<String> args = stub.getStringArgs();
        if (args.size() != 3) {
            return newErrorResponse("Incorrect number of arguments. Expecting 3");
        }
        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        try {
            _logger.info("Invoke java simple chaincode");
            String func = stub.getFunction();
            List<String> params = stub.getParameters();
            if (func.equals("Put")) {
                return Put(stub, params);
            }
            if (func.equals("Get")) {
                return Get(stub, params);
            }
            if (func.equals("getHistoryByKey")) {
                return getHistoryByKey(stub, params);
            }
            if (func.equals("getRecordByKey")) {
                return getRecordByKey(stub, params);
            }
            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"Put\", \"Get\", \"getHistoryByKey\", \"getRecordByKey\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private Response Put(ChaincodeStub stub, List<String> args){
        return newSuccessResponse(ByteString.copyFrom("put function", UTF_8).toByteArray());
        // if (args.size() != 3) {
        //     return newErrorResponse("Incorrect number of arguments. Expecting 3");
        // }
        // String accountFromKey = args.get(0);
        // String accountToKey = args.get(1);

        // String accountFromValueStr = stub.getStringState(accountFromKey);
        // if (accountFromValueStr == null) {
        //     return newErrorResponse(String.format("Entity %s not found", accountFromKey));
        // }
        // int accountFromValue = Integer.parseInt(accountFromValueStr);

        // String accountToValueStr = stub.getStringState(accountToKey);
        // if (accountToValueStr == null) {
        //     return newErrorResponse(String.format("Entity %s not found", accountToKey));
        // }
        // int accountToValue = Integer.parseInt(accountToValueStr);

        // int amount = Integer.parseInt(args.get(2));

        // if (amount > accountFromValue) {
        //     return newErrorResponse(String.format("not enough money in account %s", accountFromKey));
        // }

        // accountFromValue -= amount;
        // accountToValue += amount;

        // _logger.info(String.format("new value of A: %s", accountFromValue));
        // _logger.info(String.format("new value of B: %s", accountToValue));

        // stub.putStringState(accountFromKey, Integer.toString(accountFromValue));
        // stub.putStringState(accountToKey, Integer.toString(accountToValue));

        // _logger.info("Transfer complete");

        // return newSuccessResponse("invoke finished successfully", ByteString.copyFrom(accountFromKey + ": " + accountFromValue + " " + accountToKey + ": " + accountToValue, UTF_8).toByteArray());
    }

    // query callback representing the query of a chaincode
    private Response Get(ChaincodeStub stub, List<String> args) {
        return newSuccessResponse("invoke Get function", ByteString.copyFrom("get function", UTF_8).toByteArray());
        // if (args.size() != 1) {
        //     return newErrorResponse("Incorrect number of arguments. Expecting name of the person to query");
        // }
        // String key = args.get(0);
        // //byte[] stateBytes
        // String val	= stub.getStringState(key);
        // if (val == null) {
        //     return newErrorResponse(String.format("Error: state for %s is null", key));
        // }
        // _logger.info(String.format("Query Response:\nName: %s, Amount: %s\n", key, val));
        // return newSuccessResponse(val, ByteString.copyFrom(val, UTF_8).toByteArray());
    }

    private Response getHistoryByKey(ChaincodeStub stub, List<String> args) {
        return newSuccessResponse("invoke getHistoryByKey function");
        // if (args.size() != 1) {
        //     return newErrorResponse("Incorrect number of arguments. Expecting 1");
        // }
        // String key = args.get(0);
        // // Delete the key from the state in ledger
        // stub.delState(key);
        // return newSuccessResponse();
    }

    private Response getRecordByKey(ChaincodeStub stub, List<String> args) {
        return newSuccessResponse("invoke getRecordByKey function");
    }

    public static void main(String[] args) {
        System.out.println("OpenSSL avaliable: " + OpenSsl.isAvailable());
        new SimpleChaincode().start(args);
    }

}
