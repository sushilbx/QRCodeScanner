package com.sushil.qr.models;

import java.util.List;

public class SecondaryModel {
    public String QRCode;
    public String SecondaryContainerCode;
    public List<SeecondaryLabelDetail> SecondaryLabelDetail;

    public class SeecondaryLabelDetail {
        public String QRCode;
        public String ProductCode;
        public String BatchNumber;
        public String SerialNumber;
        public String ManufactureDate;
        public String ExpiryDate;
    }
}
