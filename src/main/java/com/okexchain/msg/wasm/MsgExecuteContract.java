package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;


public class MsgExecuteContract extends MsgBase {
    public MsgExecuteContract() {
        setMsgType("wasm/MsgExecuteContract");
    }

    public Message produceMsg(String contract, List<Funds> fundsList, String msgStr) {
        MsgExecuteContractValue value = new MsgExecuteContractValue();

        value.setContract(contract);
        value.setFunds(fundsList);
        value.setMsg(Utils.getSortJson(msgStr));
        value.setFunds(fundsList);
        value.setSender(this.address);
        Message<MsgExecuteContractValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("exchain-67");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        MsgExecuteContract msg=new MsgExecuteContract();
        msg.init(key);

        List<Funds> fundsArrayList = new ArrayList<>();
        Funds funds = new Funds();
        funds.setDenom("okt");
        funds.setAmount("1");
        fundsArrayList.add(funds);
        String msgStr ="{\"release\":{}}";

        Message message = msg.produceMsg("ex1efgvwhxkaj642uwjq65q9k3wzrghy0v2ftyap0kkwe4r3nx3846sjhluuc",fundsArrayList,msgStr);

        JSONObject res = msg.submit(message, "0.05", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
