package com.android254.droidconke19.ui.feedback

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.android254.droidconke19.R
import com.android254.droidconke19.models.UserEventFeedback
import com.android254.droidconke19.utils.nonNull
import com.android254.droidconke19.utils.observe
import com.android254.droidconke19.viewmodels.FeedBackViewModel
import kotlinx.android.synthetic.main.activity_event_feedback.*
import kotlinx.android.synthetic.main.content_event_feedback.*
import org.jetbrains.anko.toast


class EventFeedbackActivity : AppCompatActivity() {
    lateinit var feedBackViewModel: FeedBackViewModel
    private var eventFeedback: String = ""
    lateinit var userEventFeedback: UserEventFeedback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_feedback)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        feedBackViewModel = ViewModelProviders.of(this).get(FeedBackViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Event Feedback"


        //observe live data emitted by view model
        observeLiveData()

        fab.setOnClickListener {
            when {
                isFeedbackValid() -> postUserFeedback(userEventFeedback)
            }
        }

    }

    private fun observeLiveData() {
        feedBackViewModel.getEventFeedBackResponse().nonNull().observe(this) {
            handleFeedbackResponse(it)
        }
        feedBackViewModel.getEventFeedbackError().nonNull().observe(this) {
            handleDataError(it)
        }
    }

    private fun handleDataError(it: String) {
        toast(it)
    }

    private fun handleFeedbackResponse(@Suppress("UNUSED_PARAMETER") feedback: String) {
        progressBarEventFeedBack.visibility = View.GONE
        txtEventFeedback.setText("")
        toast("Thank you for your feedback")

    }

    private fun isFeedbackValid(): Boolean {

        eventFeedback = txtEventFeedback.text.toString().trim()
        val isValid: Boolean

        when {
            eventFeedback.isEmpty() -> {
                txtEventFeedback.error = "Event feedback cannot be empty"
                isValid = false
            }
            else -> {

                isValid = true
                txtEventFeedback.error = null

                userEventFeedback = UserEventFeedback(eventFeedback)

            }
        }
        return isValid
    }

    private fun postUserFeedback(userEventFeedback: UserEventFeedback) {

        progressBarEventFeedBack.visibility = View.VISIBLE
        feedBackViewModel.sendEventFeedBack(userEventFeedback)

    }
}