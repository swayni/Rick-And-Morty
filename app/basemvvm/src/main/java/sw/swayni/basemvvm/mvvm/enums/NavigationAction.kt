package sw.swayni.basemvvm.mvvm.enums

import androidx.navigation.NavDirections

sealed class NavigationAction {
    class ToDirection(val direction: NavDirections) : NavigationAction()
    data object Back : NavigationAction()
}
