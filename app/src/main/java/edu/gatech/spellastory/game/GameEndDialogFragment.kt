package edu.gatech.spellastory.game

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import kotlinx.android.synthetic.main.fragment_game_end_dialog.*

class GameEndDialogFragment : BottomSheetDialogFragment() {

    private var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_game_end_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_finish.setOnClickListener { listener?.onFinishClicked() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = if (parentFragment != null) parentFragment as Listener else context as Listener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface Listener {
        fun onFinishClicked()
    }

    companion object {
        fun newInstance(): GameEndDialogFragment {
            return GameEndDialogFragment()
        }
    }
}