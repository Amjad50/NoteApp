package com.amjad.opennote.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amjad.opennote.data.entities.CheckableListNote
import com.amjad.opennote.databinding.CheckableAddNewItemViewBinding
import com.amjad.opennote.databinding.CheckableNoteItemBinding
import com.amjad.opennote.databinding.CheckableTitleItemBinding
import com.amjad.opennote.ui.viewmodels.NoteEditViewModel

class CheckableNoteListAdapter(private val viewModel: NoteEditViewModel) :
    OffsettedListAdapter<CheckableListNote.Item, CheckableNoteListAdapter.BaseCheckableNoteItemViewHolder>(
        CheckableNoteListDiffItemCallBack(), 1, 1
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseCheckableNoteItemViewHolder {
        return when (viewType) {
            VIEWTYPE_HEADER -> TitleNoteItemViewHolder(
                CheckableTitleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEWTYPE_LIST_ITEM ->
                CheckableListNoteItemViewHolder(
                    CheckableNoteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            VIEWTYPE_FOOTER -> AddNewItemViewHolder(
                CheckableAddNewItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                throw IllegalArgumentException("Unknown type with code $viewType")
            }
        }

    }

    override fun onBindViewHolder(holder: BaseCheckableNoteItemViewHolder, position: Int) {
        when (holder) {
            is CheckableListNoteItemViewHolder -> {
                holder.bind(getItem(position))
            }
            is TitleNoteItemViewHolder -> {
                holder.bind(viewModel)
            }
            is AddNewItemViewHolder -> {
                holder.bind()
            }
        }
    }

    private inner class CheckableListNoteItemViewHolder(private val binding: CheckableNoteItemBinding) :
        BaseCheckableNoteItemViewHolder(binding.root) {

        private fun updateTextEditStrikethough(item: CheckableListNote.Item) {
            binding.textEdit.paintFlags =
                if (item.isChecked)
                    binding.textEdit.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else
                    binding.textEdit.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        fun bind(item: CheckableListNote.Item) {
            binding.item = item

            binding.setOnCheckboxChange {
                viewModel.notifyNoteUpdated()
                // when changing the state of the note
                updateTextEditStrikethough(item)
            }
            // when creating
            updateTextEditStrikethough(item)

            binding.setOnDelete {
                (viewModel.note.value as CheckableListNote?)?.noteList?.removeAt(item.position)
                viewModel.notifyNoteUpdated()
            }
        }
    }

    private class TitleNoteItemViewHolder(private val binding: CheckableTitleItemBinding) :
        BaseCheckableNoteItemViewHolder(binding.root) {
        fun bind(viewModel: NoteEditViewModel) {
            binding.model = viewModel
        }
    }

    private inner class AddNewItemViewHolder(val binding: CheckableAddNewItemViewBinding) :
        CheckableNoteListAdapter.BaseCheckableNoteItemViewHolder(binding.root) {

        fun bind() {
            binding.setOnAddNewNote {
                // TODO: make it auto focus to type immediately
                (viewModel.note.value as CheckableListNote?)?.noteList?.add(CheckableListNote.Item())
                viewModel.notifyNoteUpdated()
            }
        }

    }

    abstract class BaseCheckableNoteItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}


private class CheckableNoteListDiffItemCallBack : DiffUtil.ItemCallback<CheckableListNote.Item>() {
    override fun areContentsTheSame(
        oldItem: CheckableListNote.Item,
        newItem: CheckableListNote.Item
    ): Boolean =
        newItem.text == oldItem.text && newItem.isChecked == oldItem.isChecked


    override fun areItemsTheSame(
        oldItem: CheckableListNote.Item,
        newItem: CheckableListNote.Item
    ): Boolean =
        newItem === oldItem
}