package org.d3ifcool.laundrytelkom.ui.fiture.karpet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.adapter.recyclerview.KarpetAdapter
import org.d3ifcool.laundrytelkom.databinding.ActivityKarpetRvBinding
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceKarpet
import org.d3ifcool.laundrytelkom.model.Category

class KarpetRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKarpetRvBinding

    //    RV
    private lateinit var rvKarpet: RecyclerView
    private var list: ArrayList<Category> = arrayListOf()

    val titleActivity = "Karpet"
    var title: String = ""
    var address: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKarpetRvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvKarpet = binding.rvKarpet
        rvKarpet.setHasFixedSize(true)

        //        ACTION BAR
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = titleActivity
        actionBar?.setDisplayHomeAsUpEnabled(true)
//        Get Data From Firebase
        rvKarpet.layoutManager = LinearLayoutManager(this)
        startRecyclerView()

//        init auth
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName.toString()


        CalculatePriceKarpet.reference = FirebaseDatabase.getInstance().getReference("users")
        CalculatePriceKarpet.reference.child(name).get().addOnSuccessListener {
            if (it.exists()) {
                address = it.child("address").value.toString()
            }
        }

//        Start RecyclerView
        binding.sendMessage.setOnClickListener {
            if (CalculatePriceKarpet.checkProduct() > 0) {
                CalculatePriceKarpet.inputFirebase()
                val CHANNEL_ID = "NOTIFICATION"
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo_telkom_laundry_biru)
                    .setContentTitle("Thanks For Your Order!")
                    .setContentText("Silahkan Lanjut Ke WhatsApp Untuk Order")
                    .setPriority(NotificationCompat.PRIORITY_MAX)

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

// Create the notification channel if it does not exist
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        CHANNEL_ID,
                        "My channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    notificationManager.createNotificationChannel(channel)
                    Handler().postDelayed({
                        this.finish()
                    }, 10000)
                }

// Show the notification
                notificationManager.notify(1, builder.build())
                openWhatsApp(
                    "6281294358181",
                    "Halo Kak Saya Mau Pesan Laundry Atas Nama : $name \nDengan Alamat : $address \nIngin Order\n${CalculatePriceKarpet.getMessage()}"
                )
                Toast.makeText(
                    this,
                    "Order Sukses, Selanjutnya Chat Melalui WhatsApp",
                    Toast.LENGTH_SHORT
                ).show()
                Thread.sleep(10000)
                finish()
                CalculatePriceKarpet.deleteAllItem()
            }


        }
//
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openWhatsApp(phoneNumber: String, message: String) {
        val url = "https://api.whatsapp.com/send?phone=${phoneNumber} Number&text=${message}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun startRecyclerView() {
        CalculatePriceKarpet.reference = FirebaseDatabase.getInstance().getReference("ui").child("karpet")
        CalculatePriceKarpet.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userDataSnapshot in dataSnapshot.children) {
                        val uiData = userDataSnapshot.getValue(Category::class.java)
                        list.add(uiData!!)
                    }
                    val listKarpetAdapter = KarpetAdapter(list)
                    rvKarpet.adapter = listKarpetAdapter
                }
                Log.d("userganteng", "nama ${list}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}