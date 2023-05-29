package org.d3ifcool.laundrytelkom.ui.fiture.satuan

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
import com.google.firebase.database.*
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.adapter.recyclerview.SatuanAdapter
import org.d3ifcool.laundrytelkom.databinding.ActivitySatuanRvBinding
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceSatuan
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceSatuan.getMessage
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceSatuan.reference
import org.d3ifcool.laundrytelkom.model.Category

class SatuanRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySatuanRvBinding

    //    RV
    private lateinit var rvSatuan: RecyclerView
    private var list: ArrayList<Category> = arrayListOf()

    val titleActivity = "Satuan"
    var title: String = ""
    var address: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySatuanRvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvSatuan = binding.rvSatuan
        rvSatuan.setHasFixedSize(true)

        //        ACTION BAR
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = titleActivity
        actionBar?.setDisplayHomeAsUpEnabled(true)
//        Get Data From Firebase
        rvSatuan.layoutManager = LinearLayoutManager(this)
        startRecyclerView()

//        init auth
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName.toString()


        reference = FirebaseDatabase.getInstance().getReference("users")
        reference.child(name).get().addOnSuccessListener {
            if (it.exists()) {
                address = it.child("address").value.toString()
            }
        }

//        Start RecyclerView
        binding.sendMessage.setOnClickListener {
            if (CalculatePriceSatuan.checkProduct() > 0) {
                CalculatePriceSatuan.inputFirebase()
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
                    "Halo Kak Saya Mau Pesan Laundry Atas Nama : $name \nDengan Alamat : $address \nIngin Order\n${getMessage()}"
                )
                Toast.makeText(
                    this,
                    "Order Sukses, Selanjutnya Chat Melalui WhatsApp",
                    Toast.LENGTH_SHORT
                ).show()
                Thread.sleep(10000)
                finish()
                CalculatePriceSatuan.deleteAllItem()
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
        reference = FirebaseDatabase.getInstance().getReference("ui").child("satuan")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userDataSnapshot in dataSnapshot.children) {
                        val uiData = userDataSnapshot.getValue(Category::class.java)
                        list.add(uiData!!)
                    }
                    val listSatuanAdapter = SatuanAdapter(list)
                    rvSatuan.adapter = listSatuanAdapter
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