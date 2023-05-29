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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivityKarpetBinding
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebaseProductBajuCelana
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebaseProductTebalTipis
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class KarpetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKarpetBinding
    lateinit var reference: DatabaseReference
    lateinit var database: FirebaseDatabase
    var tipis = 0
    var tebal = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKarpetBinding.inflate(layoutInflater)
        setContentView(binding.root)
//       ACTION BAR
        val actionBar: ActionBar? = supportActionBar

        // Customize the back button

        // Customize the back button
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Menu Karpet"
        actionBar?.setDisplayHomeAsUpEnabled(true)


        binding.manyProductsTipis.text = tipis.toString()
        binding.manyProductsTebal.text = tebal.toString()


// Karpet jenis tipis
        binding.plusTipis.setOnClickListener {
            tipis += 1
            binding.manyProductsTipis.text = tipis.toString()
            Log.d("Banyak karpet", "Tipis $tipis")
        }

        binding.minusTipis.setOnClickListener {
            if (tipis > 0) {
                tipis -= 1
            }
            binding.manyProductsTipis.text = tipis.toString()
            Log.d("Banyak karpet", "Karpet $tipis")
        }


//  Karpet jenis tebal
        binding.plusTebal.setOnClickListener {
            tebal += 1
            binding.manyProductsTebal.text = tebal.toString()
            Log.d("Banyak karpet", "Standar $tebal")
        }

        binding.minusTebal.setOnClickListener {
            if (tebal > 0) {
                tebal -= 1
            }
            binding.manyProductsTebal.text = tebal.toString()
            Log.d("Banyak karpet", "Tebal $tebal")
        }

//        Init Input Database
        database = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        binding.sendMessage.setOnClickListener {
            readData(user?.displayName.toString())
            if (tipis > 0 || tebal > 0) {
                Toast.makeText(
                    this,
                    "Order Sukses, Selanjutnya Chat Melalui WhatsApp",
                    Toast.LENGTH_SHORT
                ).show()
                inputProductToFirebase(tipis.toString(), tebal.toString())
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

    fun satuanCalculatePrice(tipis: Int, tebal: Int): Int {
        var price = tipis + tebal
        return price * 4500
    }

    fun readData(userName: String) {
        reference = FirebaseDatabase.getInstance().getReference("users")
        reference.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("name").value.toString()
                val address = it.child("address").value.toString()

                openWhatsApp(
                    "6281294358181",
                    "Halo Kak Saya Mau Pesan Laundry Atas Nama : $name \nDengan Alamat : $address \nBanyak Karpet Tipis : $tipis Buahh \n Banyak Karpet Tebal : $tebal Buah \nTotal Harga : ${
                        satuanCalculatePrice(
                            tipis,
                            tebal
                        )
                    } "
                )

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


        val helperClass: UserHelperFirebaseProductTebalTipis =
            UserHelperFirebaseProductTebalTipis(
                banyakBaju, banyakCelana, satuanCalculatePrice(
                    tipis,
                    tebal
                ).toString()
            )
        reference.child(name).child("Product").child("Product Karpet").child(time)
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
