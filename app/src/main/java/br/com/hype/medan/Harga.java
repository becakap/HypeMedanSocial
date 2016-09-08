package br.com.hype.medan;

/**
 * Created by Maulana Fadhil on 9/4/2016.
 */
public class Harga {
    private String id;
    private String item;
    private String harga;
    private String gambar;



    public Harga(){
        // TODO Auto-generated constructor stub
    }

    public Harga(String id, String item, String harga, String gambar) {
        super();
        this.id = id;
        this.item = item;
        this.harga = harga;
        this.gambar = gambar;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


















}
