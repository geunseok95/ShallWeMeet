package com.professionalandroid.apps.capston_meeting.src

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class NullHostNameVerifier: HostnameVerifier {
    override fun verify(p0: String?, p1: SSLSession?): Boolean {
        return true
    }
}