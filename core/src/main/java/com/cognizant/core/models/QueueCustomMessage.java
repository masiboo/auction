package com.cognizant.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueueCustomMessage  implements Serializable {
    private String message;
    private String name;
    private int price;
    private int auctionHours;
    private byte[] pictureByte;
}
