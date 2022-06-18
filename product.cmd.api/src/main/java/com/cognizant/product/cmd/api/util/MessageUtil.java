package com.cognizant.product.cmd.api.util;


import com.cognizant.core.models.QueueCustomMessage;
import com.cognizant.product.cmd.api.commands.NewProductCommand;

public class MessageUtil {

    public static QueueCustomMessage makeCustomMessage(byte[] photoByte){
        QueueCustomMessage customMessage = new QueueCustomMessage();
        customMessage.setMessage("Sell it");
        customMessage.setPictureByte(photoByte);
        return customMessage;
    }

    public static QueueCustomMessage makeCustomMessage(NewProductCommand command){
        QueueCustomMessage customMessage = new QueueCustomMessage();
        customMessage.setPrice(command.getProduct().getPrice());
        customMessage.setName(command.getProduct().getName());
        return customMessage;
    }
}
