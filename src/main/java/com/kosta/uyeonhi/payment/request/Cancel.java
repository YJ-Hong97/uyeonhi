package com.kosta.uyeonhi.payment.request;

public class Cancel {
    public String receiptId;
    public Double price;
    public String name;
    public String reason;
    public RefundData refund;
}
