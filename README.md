# Membuat CRUD dengan ROOM

# Langkah - Langkah Konfigurasi ROOM
1. membuat tabel-tabel yang diperlukan
2. membuat Dao dari tabel yang sudah dibuat


--------
CATATAN
--------
*** Perlu untuk diperhatikan pula bahwa untuk melakukan operasi seperti memasukkan data,
    mengambil data dan lain-lain harus dilakukan di luar mainthread dan ini adalah ada aturan Room secara default.
    Hal ini disebakan karena melakukan query data pada mainthread dapat membuat aplikasi kita menjadi lambat.
    Walaupun room menyediakan method allowMainThreadQueries() namun hal ini tidak disarankan.
    Maka dari itu kita perlu membuat proses ini berjalan di luar mainthread. Sebenarnya banyak pilihan untuk melakukan ini seperti menggunakan Asynctask, Handler atau RxJava.
    Pada tutorial ini saya akan menggunakan RxJava :).


