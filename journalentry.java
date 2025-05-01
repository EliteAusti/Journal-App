public class journalentry {
    private String date;
    private String entry;
    public journalentry(String date1,String entry1){
        this.date=date1;
        this.entry=entry1;
    }
    public String getEntry(){
        return this.entry;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date1){
        this.date=date1;
    }
    public void setEntry(String entry1){
        this.entry=entry1;
    }
}
