package com.denizhan.fotografpaylasmafirebase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denizhan.fotografpaylasmafirebase.view.KullaniciActivity
import com.denizhan.fotografpaylasmafirebase.R
import com.denizhan.fotografpaylasmafirebase.adapter.HaberRecyclerAdapter
import com.denizhan.fotografpaylasmafirebase.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_haberler.*
import kotlinx.android.synthetic.main.recycler_raw.*

class HaberlerActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter: HaberRecyclerAdapter

    var postListesi = ArrayList<Post>() //Post sınıfının elemanları kullanılır


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_haberler)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        recyclerView2.layoutManager = layoutManager
        recyclerViewAdapter = HaberRecyclerAdapter(postListesi)
        recyclerView2.adapter = recyclerViewAdapter
    }

    //Firebase'daki POST isimli koleksiyondan veri alır
    fun verileriAl(){
        database.collection("POST").orderBy("tarih",Query.Direction.DESCENDING) //tarihe göre sırala - decenfing ile en yeni tarihli olan sıralanır

            .addSnapshotListener{snapshot, exception ->
            if(exception != null){
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else{
                if(snapshot != null){
                    if(!snapshot.isEmpty){
                        val documents = snapshot.documents //liste alındı

                        postListesi.clear() //önceden içeriden kalanı temizler
                        for(document in documents){
                            var kullaniciemail = document.get("kullaniciemail") as String //any değeri string gibi gösterdi
                            var kullaniciyorum = document.get("kullaniciyorum") as String // any to string
                            var gorselUrl = document.get("gorselurl") as String // any to string

                            var indirilenPost = Post(kullaniciemail,kullaniciyorum,gorselUrl)
                            postListesi.add(indirilenPost)
                            //println(kullaniciemail)
                        }
                        recyclerViewAdapter.notifyDataSetChanged() //kendini yenile
                    }
                }

            }
        }

    }


    //menu bagladik
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)

        return super.onCreateOptionsMenu(menu)
    }





    //menudeki işlevler
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fotograf_paylas){
            //fotograf paylamaya gidersin
            val intent = Intent(this, FotografPaylasmaActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.cikis_yap){

            auth.signOut() // firebase cikis yap
            //cikis yaparsin
            val intent = Intent(this, KullaniciActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }

}