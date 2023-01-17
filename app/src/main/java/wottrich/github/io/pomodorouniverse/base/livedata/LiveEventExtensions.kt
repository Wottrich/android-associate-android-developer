package wottrich.github.io.pomodorouniverse.base.livedata

fun MutableSingleLiveEvent<Unit>.emit() {
    emit(Unit)
}

fun MutableSingleLiveEvent<Unit>.post() {
    post(Unit)
}
