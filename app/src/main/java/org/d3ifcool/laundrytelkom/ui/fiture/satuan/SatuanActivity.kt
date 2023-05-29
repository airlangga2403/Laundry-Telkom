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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivitySatuanBinding
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebaseProductBajuCelana
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class SatuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySatuanBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    var baju = 0
    var celana = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySatuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ACTION BAR
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Menu Satuan"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.manyProductsBaju.text = baju.toString()
        binding.manyProductsCelana.text = celana.toString()


// BAJU
        binding.plusBaju.setOnClickListener {
            baju += 1
            binding.manyProductsBaju.text = baju.toString()
            Log.d("Banyak baju", "Baju $baju")
        }

        binding.minusBaju.setOnClickListener {
            if (baju > 0) {
                baju -= 1
            }
            binding.manyProductsBaju.text = baju.toString()
            Log.d("Banyak baju", "Baju $baju")
        }

//  Celana
        binding.plusCelana.setOnClickListener {
            celana += 1
            binding.manyProductsCelana.text = celana.toString()
            Log.d("Banyak celana", "Celana $celana")
        }

        binding.minusCelana.setOnClickListener {
            if (celana > 0) {
                celana -= 1
            }
            binding.manyProductsCelana.text = celana.toString()
            Log.d("Banyak celana", "Celana $celana")
        }
//        Init Input Database
        database = FirebaseDatabase.getInstance()
//        Init User
        val user = FirebaseAuth.getInstance().currentUser
        binding.sendMessage.setOnClickListener {
            readData(user?.displayName.toString())
//            Input To Firebase
            if (baju > 0 || celana > 0) {
                inputProductToFirebase(baju.toString(), celana.toString())
                val CHANNEL_ID = "NOTIFICATION"
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo_telkom_laundry_biru)
                    .setContentTitle("Thanks For Your Order!")
                    .setContentText("Silahkan Lanjut Ke WhatsApp Untuk Order")
                    .setPriority(NotificationCompat.PRIORITY_MAX)

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun openWhatsApp(phoneNumber: String, message: String) {
        val url = "https://api.whatsapp.com/send?phone=${phoneNumber} Number&text=${message}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun satuanCalculatePrice(baju: Int, celana: Int): Int {
        var price = baju + celana
        return price * 1000
    }

    fun readData(userName: String) {
        reference = FirebaseDatabase.getInstance().getReference("users")
        reference.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value.toString()
                val address = it.child("address").value.toString()
                if (baju > 0 || celana > 0) {
                    Toast.makeText(
                        this,
                        "Order Sukses, Selanjutnya Chat Melalui WhatsApp",
                        Toast.LENGTH_SHORT
                    ).show()
                    openWhatsApp(
                        "6281294358181",
                        "Halo Kak Saya Mau Pesan Laundry Atas Nama : $name \nDengan Alamat : $address \nBanyak Baju : $baju Buahh \n Banyak Celana : $celana Buah \nTotal Harga : ${
                            satuanCalculatePrice(
                                baju,
                                celana
                            )
                        } "
                    )
                } else {
                    Toast.makeText(
                        this,
                        "Silahkan Add Baju Atau Celana Terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this,
                    "User Doesn't Exist Username :  ${userName}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputProductToFirebase(banyakBaju: String, banyakCelana: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName.toString()

        reference = database.getReference("users")

        val time = getTime()


        val helperClass: UserHelperFirebaseProductBajuCelana =
            UserHelperFirebaseProductBajuCelana(
                banyakBaju, banyakCelana, satuanCalculatePrice(
                    baju,
                    celana
                ).toString()
            )
        reference.child(name).child("Product").child("Product Satuan").child(time)
            .setValue(helperClass)
    }

    private fun getTime(): String {
        var answer = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss")
            answer = current.format(formatter)

        } else {
            var date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            answer = formatter.format(date)
        }
        return answer
    }
}