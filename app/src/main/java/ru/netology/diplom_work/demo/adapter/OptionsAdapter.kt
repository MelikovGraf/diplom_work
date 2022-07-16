package ru.netology.diplom_work.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.diplom_work.activity.Cuisine
import ru.netology.diplom_work.databinding.ItemOptionsBinding


class OptionsAdapter() : RecyclerView.Adapter<OptionsAdapter.SettingHolder>() {

    var selectedKitchenList: MutableList<Cuisine> = mutableListOf()
        get() = Cuisine.selectedKitchenList
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = selectedKitchenList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOptionsBinding.inflate(inflater, parent, false)
        return SettingHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingHolder, position: Int) {
        val selectedKitchen = selectedKitchenList[position]
        with(holder.binding) {
            checkbox.text = selectedKitchen.title
            checkbox.isChecked = selectedKitchen.isChecked
        }
        holder.binding.checkbox.setOnClickListener {
            selectedKitchen.isChecked = !selectedKitchen.isChecked
        }
    }

    class SettingHolder(
        val binding: ItemOptionsBinding
    ) : RecyclerView.ViewHolder(binding.root)
}