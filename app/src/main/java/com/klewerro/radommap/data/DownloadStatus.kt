package com.klewerro.radommap.data

enum class DownloadStatus(val value: Int) {
    STARTED(0),
    PROGRESS_1(1),
    FINISHED_SUCCESSFULLY(2),
    ERROR(-1),
    CACHE(-2);

    companion object {
        private val VALUES = values();
        fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
    }
}