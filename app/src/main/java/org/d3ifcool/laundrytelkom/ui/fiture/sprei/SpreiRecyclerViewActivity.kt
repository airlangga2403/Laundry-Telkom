package org.d3ifcool.laundrytelkom.ui.fiture.sprei

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
import org.d3ifcool.laundrytelkom.adapter.recyclerview.SpreiAdapter
import org.d3ifcool.laundrytelkom.databinding.ActivitySetrikaRvBinding
import org.d3ifcool.laundrytelkom.databinding.ActivitySpreiRvBinding
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceSetrika
import org.d3ifcool.laundrytelkom.model.Category

class SpreiRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpreiRvBinding

    //    RV
    private lateinit var rvSprei: RecyclerView
    private var list: ArrayList<Category> = arrayListOf()

    val titleActivity = "Sprei"
    var title: String = ""
    var address: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        BINDING FROM SATUAN
        binding = ActivitySpreiRvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvSprei = binding.rvSprei
        rvSprei.setHasFixedSize(true)

        //        ACTION BAR
        val actionBar: ActionBar? = supportActionBar
        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = titleActivity
        actionBar?.setDisplayHomeAsUpEnabled(true)
//        Get Data From Firebase
        rvSprei.layoutManager = LinearLayoutManager(this)
        startRecyclerView()

//        init auth
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName.toString()


        CalculatePriceSetrika.reference = FirebaseDatabase.getInstance().getReference("users")
        CalculatePriceSetrika.reference.child(name).get().addOnSuccessListener {
            if (it.exists()) {
                address = it.child("address").value.toString()
            }
        }
        binding.sendMessage.setOnClickListener {
            if (CalculatePriceSetrika.checkProduct() > 0) {
                CalculatePriceSetrika.inputFirebase()
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
                    "Halo Kak Saya Mau Pesan Laundry Atas Nama : $name \nDengan Alamat : $address \nIngin Order\n${CalculatePriceSetrika.getMessage()}"
                )
                Toast.makeText(
                    this,
                    "Order Sukses, Selanjutnya Chat Melalui WhatsApp",
                    Toast.LENGTH_SHORT
                ).show()
                Thread.sleep(10000)
                finish()
                CalculatePriceSetrika.deleteAllItem()
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
        CalculatePriceSetrika.reference = FirebaseDatabase.getInstance().getReference("ui").child("sprei")
        CalculatePriceSetrika.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userDataSnapshot in dataSnapshot.children) {
                        val uiData = userDataSnapshot.getValue(Category::class.java)
                        list.add(uiData!!)
                    }
                    val listSpreiAdapter = SpreiAdapter(list)
                    rvSprei.adapter = listSpreiAdapter
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
