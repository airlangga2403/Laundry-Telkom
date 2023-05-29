package org.d3ifcool.laundrytelkom.adapter.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.helper.calc.CalculatePriceSprei
import org.d3ifcool.laundrytelkom.model.Category


class SpreiAdapter(private val listCategorySprei: ArrayList<Category>) :
    RecyclerView.Adapter<SpreiAdapter.ListViewHolder>() {


    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpreiAdapter.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_satuan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpreiAdapter.ListViewHolder, position: Int) {
        val category = listCategorySprei[position]
        var baju = 0
        if (baju<0){
            baju = 0
        }
        Glide.with(holder.itemView.context)
            .load(category.img)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)
        holder.tvName.text = category.title

        holder.plusImg.setOnClickListener {
            baju += 1
            holder.manyProduct.text = baju.toString()
            CalculatePriceSprei.plusProduct(
                holder.tvName.text.toString(),
                baju,
                category.harga
            )
            Log.d("PRODUCT", "bnyk baju $baju ${category.title}")

        }

        holder.minusImg.setOnClickListener {
            if (holder.manyProduct.text.toString().toInt() > 0) {
                if (holder.tvName.text == category.title) {
                    baju -= 1
                    holder.manyProduct.text = baju.toString()
                    CalculatePriceSprei.minusProduct()
                    Log.d("PRODUCT", "bnyk baju $baju ${category.title}")
                }
            } else if(holder.manyProduct.text.toString().toInt() < 0){
                holder.manyProduct.text == "0"
            }
        }
    }

    override fun getItemCount(): Int {
        return listCategorySprei.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.title)
        var imgPhoto: ImageView = itemView.findViewById(R.id.baju)

        //
        var plusImg: ImageView = itemView.findViewById(R.id.plus_baju)
        var minusImg: ImageView = itemView.findViewById(R.id.minus_baju)
        var manyProduct: TextView = itemView.findViewById(R.id.many_products_baju)

    }

}