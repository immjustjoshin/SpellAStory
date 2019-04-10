package edu.gatech.spellastory.bank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.Words
import edu.gatech.spellastory.domain.Word
import kotlinx.android.synthetic.main.activity_bank.*

fun Context.BankIntent() = Intent(this, BankActivity::class.java)

class BankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)

        rv_bank.adapter = BankAdapter(Words.getInstance(this).completedWords)
        rv_bank.layoutManager = LinearLayoutManager(this)
        rv_bank.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
