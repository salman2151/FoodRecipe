package com.murata.foodrecipeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murata.foodrecipeapp.databinding.ItemTagBinding
import com.murata.foodrecipeapp.network.dto.DtoTag

class TagsAdapter(
    private val arrayList: ArrayList<DtoTag>,
    private val tagCallback: TagCallback
) :
    RecyclerView.Adapter<TagsAdapter.ItemTagViewHolder>() {
    private var lastSelectedIndex = -1

    inner class ItemTagViewHolder(private val itemTagBinding: ItemTagBinding) :
        RecyclerView.ViewHolder(itemTagBinding.root) {
        fun bind(dtoTag: DtoTag, position: Int) {
            itemTagBinding.dtoTag = dtoTag
            itemTagBinding.clMain.setOnClickListener {
                if (lastSelectedIndex !== -1) {
                    arrayList[lastSelectedIndex].isChecked = false
                    notifyItemChanged(lastSelectedIndex)
                }
                lastSelectedIndex = position
                dtoTag.isChecked = true
                notifyItemChanged(lastSelectedIndex)
                tagCallback.onTagSelected(dtoTag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTagViewHolder {
        return ItemTagViewHolder(
            ItemTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemTagViewHolder, position: Int) {
        holder.bind(arrayList[position], position)
    }

    override fun getItemCount() = arrayList.size
    fun setSelectedIndex(i: Int) {
        lastSelectedIndex = i;
        arrayList[lastSelectedIndex].isChecked = true
        notifyItemChanged(lastSelectedIndex)
        tagCallback.onTagSelected(arrayList[lastSelectedIndex])
    }

    interface TagCallback {
        fun onTagSelected(dtoTag: DtoTag)
    }
}
//class TagsAdapter {
//}