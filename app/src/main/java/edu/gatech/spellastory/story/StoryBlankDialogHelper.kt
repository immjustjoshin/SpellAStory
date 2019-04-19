package edu.gatech.spellastory.story

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.Words
import edu.gatech.spellastory.domain.Category
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.BaseDialogHelper
import kotlinx.android.synthetic.main.dialog_story_blank.view.*

fun Activity.StoryBlankDialog(
    categories: List<Category>,
    completedClickListener: (Word) -> Unit,
    incompleteClickListener: (Word) -> Unit,
    func: StoryBlankDialogHelper.() -> Unit
): AlertDialog =
    StoryBlankDialogHelper(this, categories, completedClickListener, incompleteClickListener)
        .apply { func() }.create()

class StoryBlankDialogHelper(
    context: Context,
    private val categories: List<Category>,
    private val completedClickListener: (Word) -> Unit,
    private val incompleteClickListener: (Word) -> Unit
) : BaseDialogHelper() {

    override val dialogView: View by lazy {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_story_blank, null)

        view.rv_complete.adapter =
            StoryBlankAdapter(
                Words.getInstance(context).getCompletedWordsForCategories(categories),
                true,
                completedClickListener
            )
        view.rv_complete.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.rv_incomplete.adapter =
            StoryBlankAdapter(
                Words.getInstance(context).getIncompleteWordsForCategories(categories),
                false,
                incompleteClickListener
            )
        view.rv_incomplete.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)
}