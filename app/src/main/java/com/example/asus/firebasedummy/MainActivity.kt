package com.example.asus.firebasedummy

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView



import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink

class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var button: Button? = null
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        editText!!.setText("https://www.kaskus.co.id/")
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        button!!.setOnClickListener { buildReferral() }


    }

    fun buildReferral() {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(editText!!.text.toString()))
                .setDynamicLinkDomain("firebasedummy.page.link")
                //.setDomainUriPrefix("firebasedummy.page.link")
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                .setGoogleAnalyticsParameters(
                        DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("referral")
                                .setContent("content")
                                .setMedium("Android")
                                .build())
                .buildDynamicLink()
        buildShortUrl(dynamicLink)
    }

    fun buildShortUrl(dynamicLink: DynamicLink) {
        val shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(editText!!.text.toString()))
                .setDynamicLinkDomain("firebasedummy.page.link")
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Short link created
                        val shortLink = task.result!!.shortLink
                        val flowchartLink = task.result!!.previewLink

                        textView!!.text = shortLink.toString()
                    } else {
                        // Error
                        // ...
                    }
                }
    }
}
