package com.berkberber.hms_securewebbrowser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berkberber.hms_securewebbrowser.data.model.MaliciousApps
import com.berkberber.hms_securewebbrowser.databinding.ItemMaliciousAppBinding
import com.berkberber.hms_securewebbrowser.ui.malicious.DeleteClickListener
import org.koin.core.component.KoinComponent

class MaliciousAppsAdapter(private var maliciousAppList: List<MaliciousApps>, private val deleteClickListener: DeleteClickListener):
    RecyclerView.Adapter<MaliciousAppsAdapter.MaliciousAppViewHolder>(), KoinComponent{

    private lateinit var binding: ItemMaliciousAppBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaliciousAppViewHolder {
        binding = ItemMaliciousAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaliciousAppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaliciousAppViewHolder, position: Int) {
        var maliciousApp = maliciousAppList[position]
        holder.bindMaliciousApp(maliciousApp)

        holder.binding.maliciousAppRoot.setOnClickListener {
            deleteClickListener.selectedApp(maliciousApp)
        }
    }

    override fun getItemCount(): Int = maliciousAppList.size

    inner class MaliciousAppViewHolder(val binding: ItemMaliciousAppBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bindMaliciousApp(maliciousApp: MaliciousApps){
            binding.maliciousApp = maliciousApp
            binding.executePendingBindings()
        }
    }
}