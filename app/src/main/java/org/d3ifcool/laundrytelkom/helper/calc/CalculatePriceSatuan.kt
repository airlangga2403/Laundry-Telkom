package org.d3ifcool.laundrytelkom.helper.calc

import android.os.Build
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.helper.UserHelperFirebaseProductBajuCelanaRv
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object CalculatePriceSatuan {
    var tipeArrayList: MutableList<String> = mutableListOf()
    var banyakProductArrayList: MutableList<String> = mutableListOf()
    var hargaProductArrayList: MutableList<String> = mutableListOf()
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference

    var tampungDataMessageArrayList: MutableList<String> = mutableListOf()




    fun checkProduct() : Int{
        val iteration = banyakProductArrayList.size
        var banyakProduct = 0
        for (a in 0 until iteration) {
            banyakProduct = banyakProductArrayList[a].toInt()
        }
        return banyakProduct
    }

    fun plusProduct(tipe: String, banyak: Int, harga: Int) {
        tipeArrayList.add(tipe)
        banyakProductArrayList.add(banyak.toString())
        hargaProductArrayList.add(harga.toString())
//        val pendapatan = banyak * price
//        priceArrayList.add(pendapatan.toString())
        Log.d("PRODUCT", "harga $tipe dan ")
    }

    fun minusProduct() {
        tipeArrayList.remove(tipeArrayList.last())
        banyakProductArrayList.remove(banyakProductArrayList.last())
        hargaProductArrayList.remove(hargaProductArrayList.last())
    }


    fun inputFirebase() {

        val iteration = tipeArrayList.size
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName.toString()
        var lastPrice = 0

        for (a in 0 until iteration) {
            val tipeProduct = tipeArrayList[a]
            val banyakProduct = banyakProductArrayList[a]
            val hargaProduct = hargaProductArrayList[a]
            val result = buildString{
                append("Tipe Product : Satuan")
                append(" | Banyak : $banyakProduct\n")
            }
            tampungDataMessageArrayList.add(result)

            val totalPrice = banyakProduct.toInt() * hargaProduct.toInt()
            lastPrice += totalPrice

            reference = FirebaseDatabase.getInstance().getReference("users")

            val time = getTime()
            val helperClass: UserHelperFirebaseProductBajuCelanaRv =
                UserHelperFirebaseProductBajuCelanaRv(
                    banyakProduct, totalPrice.toString()
                )
            reference.child(name).child("Product").child("Product Satuan").child(time)
                .child(tipeProduct)
                .setValue(helperClass)


        }
        val totalPrice = buildString{
            append("Total Harga : $lastPrice \n")
        }
        tampungDataMessageArrayList.add(totalPrice)
        Log.d(
            "productx",
            " $tampungDataMessageArrayList"
        )
    }

    fun getMessage() : String {
        val mergedString = tampungDataMessageArrayList.joinToString(" ")
        return mergedString
    }

    fun deleteAllItem(){
        tampungDataMessageArrayList.clear()
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