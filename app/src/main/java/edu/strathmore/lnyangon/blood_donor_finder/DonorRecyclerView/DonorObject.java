package edu.strathmore.lnyangon.blood_donor_finder.donorRecyclerView;

public class DonorObject {
    private String name;
    private String phone;
    private String blood_group;

    public DonorObject(String name, String phone, String blood_group){
        this.name = name;
        this.phone = phone;
        this.blood_group = blood_group;
    }

    public String getName(){return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone(){return phone;}
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood_group(){return blood_group;}
    public void setBlood_group(String blood_group){
        this.blood_group = blood_group;
    }

}
