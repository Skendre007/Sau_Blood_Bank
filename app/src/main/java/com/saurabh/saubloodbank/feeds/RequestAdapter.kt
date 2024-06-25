package com.saurabh.saubloodbank.feeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saurabh.saubloodbank.R
import com.saurabh.saubloodbank.bloodlist.data.DataRequest
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Locale

class RequestAdapter(private val context: Context,private var request: List<DataRequest>, val id:String,val flag:Boolean) :
    RecyclerView.Adapter<RequestAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.requestlistrow, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestAdapter.MyViewHolder, position: Int) {
        val currentItem: DataRequest = request[position]
        val time=convertTo12HourFormat(currentItem.Time.toString())
        val date=formatDate(currentItem.Date.toString())
        holder.pname.text = "Patient Name: ${currentItem.patient}"
        holder.phone.text=": ${currentItem.phone}"
        holder.address.text = "Hospital: ${currentItem.Hname} \nLocation: ${currentItem.Location}"
        holder.amount.text = "Blood Needed: ${currentItem.amuont}  Bag"
        holder.datetime.text = "Date: ${date} || Time: ${time}"
        holder.type.text = "Nedded ${currentItem.Blood}"
      if(flag)  holder.delete.visibility=View.VISIBLE
        else holder.delete.visibility=View.INVISIBLE

        holder.delete.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("requests")

            val userId = id

             val query = reference.orderByChild("id").equalTo(id)


            reference.child(id).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                      Toast.makeText(context,  "success", Toast.LENGTH_SHORT).show()
                } else {
                     val exception = task.exception
                    Toast.makeText(context,  "failed", Toast.LENGTH_SHORT).show()
                 }
            }
        }

        }




    override fun getItemCount(): Int {
        return request.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pname: TextView = itemView.findViewById(R.id.patientName)
        val phone: TextView = itemView.findViewById(R.id.phonenumberR)
        val amount: TextView = itemView.findViewById(R.id.BloodRequired)
        val address: TextView = itemView.findViewById(R.id.address)
        val datetime: TextView = itemView.findViewById(R.id.dateTime)
        val type: TextView = itemView.findViewById(R.id.bloodtypereq)
        val delete:ImageView=itemView.findViewById(R.id.delete)
    }
    private fun convertTo12HourFormat(time24Hour: String): String {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(time24Hour)
        return outputFormat.format(date)
    }

     private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("ddMMMyyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }


}
