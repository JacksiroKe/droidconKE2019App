package com.android254.droidconke19.ui.sessiondetails

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.android254.droidconke19.R
import com.android254.droidconke19.models.SessionsModel
import com.android254.droidconke19.models.SpeakersModel
import com.android254.droidconke19.ui.speakers.SpeakersAdapter
import com.android254.droidconke19.utils.nonNull
import com.android254.droidconke19.utils.observe
import com.android254.droidconke19.viewmodels.SessionDetailsViewModel
import kotlinx.android.synthetic.main.fragment_session_details.*
import kotlinx.android.synthetic.main.fragment_speaker.*

class SessionDetailsFragment : Fragment() {
    private val sessionDetailsViewModel : SessionDetailsViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_session_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //TODO add logic to fetch speaker details from firebase
        displaySessionSpeakers()

        bottom_app_bar.replaceMenu(R.menu.menu_bottom_appbar)
        handleBottomBarMenuClick()

        //observer live data
        observeLiveData()
    }
    private fun observeLiveData() {
        sessionDetailsViewModel.getSessionDetails().nonNull().observe(this){sessionModel ->
            setupViews(sessionModel)
        }
    }

    private fun setupViews(sessionModel: SessionsModel) {
        sessionTimeRoomText.text = getString(R.string.session_room_and_session_duration,sessionModel.duration,sessionModel.room)
        sessionStartTimeText.text = sessionModel.time
        sessionDescriptionText.text = sessionModel.description
        intendedAudienceText.text = sessionModel.session_audience

    }

    private fun handleBottomBarMenuClick() {
        bottom_app_bar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            when(id){
                 R.id.action_share ->{
                     val shareSession = Intent()
                     shareSession.action = Intent.ACTION_SEND
                     shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" + "This session" + "' at " + getString(R.string.droidcoke_hashtag) + "\n" + getString(R.string.droidconke_site))
                     shareSession.type = "text/plain"
                     startActivity(shareSession)
                }
            }
            when(id){
                R.id.action_map ->{

                }
            }
            when(id){
                R.id.action_calendar ->{
                    val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, "8:00")
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, "8:00")
                            .putExtra(CalendarContract.Events.TITLE, "droidconKE: Session Title")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "Ihub,Senteu Plaza")
                    activity?.startActivity(intent)
                }
            }

            false
        }
    }

    private fun displaySessionSpeakers() {
        val speakersList = ArrayList<SpeakersModel>()
        speakersList.add(SpeakersModel(1,"John Doe","This is bio","Company","url"))
        session_speakers.adapter = SpeakersAdapter(speakersList){
            val extras = FragmentNavigatorExtras(
                    speaker_image to "speakerImage"
            )
            findNavController().navigate(R.id.action_sessionDetailsFragment_to_speakerFragment,null,null,extras)
        }
    }
}