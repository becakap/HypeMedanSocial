package br.com.hype.medan;

/**
 * Created by IQBAL on 9/7/2016.
 */
public class Event {

    private String id;
    private String nama;
    private String gambar;


    public Event(){
        // TODO Auto-generated constructor stub
    }


    public Event(String id, String nama, String gambar) {
        super();
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }












}
