package com.example.parkprojfinal;

public class StatusBooking {
        private String slot;
        private boolean status;

        public StatusBooking(){

        }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public StatusBooking(String slot, boolean status) {
            this.slot = slot;
            this.status = status;
        }


        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }
    }

