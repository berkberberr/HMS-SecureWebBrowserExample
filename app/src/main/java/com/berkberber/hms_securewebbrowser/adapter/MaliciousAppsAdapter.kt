package com.berkberber.hms_securewebbrowser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berkberber.hms_securewebbrowser.data.model.MaliciousApps
import com.berkberber.hms_securewebbrowser.databinding.ItemMaliciousAppBinding
import org.koin.core.component.KoinComponent

class MaliciousAppsAdapter(private var maliciousAppList: List<MaliciousApps>):
    RecyclerView.Adapter<MaliciousAppsAdapter.MaliciousAppViewHolder>(), KoinComponent{

    private lateinit var binding: ItemMaliciousAppBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaliciousAppViewHolder {
        binding = ItemMaliciousAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaliciousAppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaliciousAppViewHolder, position: Int) {
        var maliciousApp = maliciousAppList[position]
        holder.bindMaliciousApp(maliciousApp)
    }

    override fun getItemCount(): Int = maliciousAppList.size

    inner class MaliciousAppViewHolder(private val binding: ItemMaliciousAppBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bindMaliciousApp(maliciousApp: MaliciousApps){
            binding.maliciousApp = maliciousApp
            binding.executePendingBindings()
        }
    }
}