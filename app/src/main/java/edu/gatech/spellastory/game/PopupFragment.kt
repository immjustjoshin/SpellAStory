package edu.gatech.spellastory.game

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import kotlinx.android.synthetic.main.fragment_game_end_dialog.*

class PopupFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_game_end_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_finish.setOnClickListener { onFinishClicked() }
    }

    var onFinishClicked: () -> Unit = {}

    companion object {
        fun newInstance(onFinishClicked: () -> Unit): PopupFragment {
            val f = PopupFragment()
            f.onFinishClicked = onFinishClicked
            return f
        }
    }
}