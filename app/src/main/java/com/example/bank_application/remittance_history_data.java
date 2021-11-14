package com.example.bank_application;

import java.io.Serializable;

public class remittance_history_data implements Serializable {
    String sendAddress;
    String receiveAddress;
    String sendName;
    String receiveName;
    String date;

    public String getAddress() {
        return address; }
    public void setAddress(String address) {
        this.address = address; }
    String address;
    long money;
    public String getSendAddress() {
        return sendAddress; }
    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress; }
    public String getReceiveAddress() {
        return receiveAddress; }
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress; }
    public String getSendName() {
        return sendName; }
    public void setSendName(String sendName) {
        this.sendName = sendName; }
    public String getReceiveName() {
        return receiveName; }
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName; }
    public String getDate() {
        return date; }
    public void setDate(String date) {
        this.date = date; }
    public long getMoney() {
        return money; }
    public void setMoney(long money) {
        this.money = money; }
}
