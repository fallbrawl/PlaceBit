package com.example.placebit.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.placebit.R
import com.example.placebit.util.ContextKeeper
import com.example.placebit.util.MultipleClickPreventer

fun View?.onClick(function: (View) -> Unit) {
    this?.setOnClickListener {
        if (!MultipleClickPreventer.allowClick()) {
            return@setOnClickListener
        }
        function(it)
    }
}


/** Convenience extension for setting a long click listener */
@Suppress("NOTHING_TO_INLINE")
inline fun View.onLongClick(noinline l: (v: View?) -> Boolean) {
    setOnLongClickListener(l)
}

/** Set this view's visibility to View.VISIBLE **/
fun <T : View> T.setVisible(isVisible: Boolean? = null): T = apply {
    visibility = if (isVisible != false) View.VISIBLE else View.GONE
}

/** Set this view's visibility to View.INVISIBLE **/
fun <T : View> T.setInvisible(): T = apply { visibility = View.INVISIBLE }

/** Set this view's visibility to View.GONE **/
fun <T : View> T.setGone(): T = setVisible(false)

/** Show a toast with a default length of Toast.LENGTH_SHORT */
fun View.toast(messageResId: Int, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(context, messageResId, length).show()

/** Converts float DIP value to pixel value */
fun Context.DP(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)

/** Converts Int DIP value to pixel value */
fun Context.DP(value: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), resources.displayMetrics)

/** Converts float DIP value to pixel value */
fun Context.SP(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, resources.displayMetrics)

/** Converts Int DIP value to pixel value */
fun Context.SP(value: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), resources.displayMetrics)

/** Converts float Pixel value to DIP value */
fun Context.PX(px: Int) = (px / resources.displayMetrics.density).toInt()

/** Converts float Pixel value to DIP value */
fun Context.PX(px: Float) = (px / resources.displayMetrics.density).toInt()

fun Context.toast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun EditText.onTextChanged(listener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }
    })
}

/**
 * Returns true if the view's layout direction is Right-To-Left, false otherwise
 */
fun View.isRTL() = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

/** Convenience extension property for getting the MeasureSpec size */
val Int.specSize get() = View.MeasureSpec.getSize(this)
/** Convenience extension property for getting the MeasureSpec mode */
val Int.specMode get() = View.MeasureSpec.getMode(this)

/** Returns a list of all immediate child views in this ViewGroup */
val View.children: List<View>
    get() = (this as? ViewGroup)?.let { (0 until childCount).map { getChildAt(it) } } ?: emptyList()

/** Returns a list of all immediate child views of the specified type in this ViewGroup */
inline fun <reified T : View> View.children(): List<T> = children.filterIsInstance<T>()

/** Returns a list of all views in this ViewGroup */
val View.descendants: List<View> get() = children + children<ViewGroup>().flatMap { it.descendants }

/** Returns a list of all views of the specified type in this ViewGroup */
inline fun <reified T : View> View.descendants(): List<T> = descendants.filterIsInstance<T>()

/** Returns the firstAncestor of the specified type, or null if there are no matches */
inline fun <reified V : View> View.firstAncestorOrNull(): V? {
    var p: ViewParent? = parent
    while (p != null) {
        if (p is V) {
            return p
        } else {
            p = p.parent
        }
    }
    return null
}

/**
 * Returns the vertical pixel offset of the top of this view inside the specified ViewGroup.
 * Returns 0 if the ViewGroup is not an ancestor of this view.
 */
fun View.topOffsetIn(ancestor: ViewGroup): Int {
    var offset = top
    var p: ViewParent? = parent
    while (p != null && p is ViewGroup) {
        if (p === ancestor) {
            return offset
        } else {
            offset += p.top
            p = p.parent
        }
    }
    return 0
}

/** Convenience property which wraps getLocationOnScreen() */
val View.positionOnScreen: Pair<Int, Int>
    get() {
        val arr = intArrayOf(0, 0)
        getLocationOnScreen(arr)
        return Pair(arr[0], arr[1])
    }

/**
 * [Binder] is a delegation class for manual view binding, useful for cases not covered by Kotlin
 * Android Extensions. Generally, bound view properties will not directly instantiate this class.
 * Instead, instantiation of a Binder instance should be handled by an extension function of the
 * class in which views are to be bound, e.g. Dialog.[bind].
 *
 * Example of how to use [Binder] in classes that have a bind() extension function:
 * ```
 * val myImageView by bind<ImageView>(R.id.my_image_view)
 * ```
 *
 * If your layout has multiple views with the same id but different parents:
 * ```
 * val myImageView1 by bind<ImageView>(R.id.my_image_view).withParent(R.id.parent_one)
 * val myImageView2 by bind<ImageView>(R.id.my_image_view).withParent(R.id.parent_two)
 * ```
 * Or, if you already have a reference to the parent views:
 * ```
 * val myImageView1 by bind<ImageView>(R.id.my_image_view).withParent { parentLayout1 }
 * val myImageView2 by bind<ImageView>(R.id.my_image_view).withParent { parentLayout2 }
 * ```
 *
 * For examples on how to create a bind() extension function for a new class, refer to Dialog.bind(),
 * ViewGroup.bind(), Activity.bind(), or Fragment.bind().
 *
 */
@Suppress("UNCHECKED_CAST")
class Binder<in T, out V : View>(@IdRes private val viewId: Int, private val finder: (T, Int) -> View?) : kotlin.properties.ReadOnlyProperty<T, V> {

    private var cachedView: V? = null
    private var useParent = false
    private var parentId: Int? = null
    private var parentProvider: (() -> View)? = null

    override fun getValue(thisRef: T, property: kotlin.reflect.KProperty<*>): V {
        if (cachedView == null) {
            val v: View
            if (useParent) {
                v = when {
                    parentProvider != null -> {
                        val parentView = parentProvider!!.invoke()
                        parentView.findViewById(viewId)
                                ?: throw RuntimeException("Unable to bind ${property.name}; view not found in provided parent ${parentView.javaClass.simpleName}")
                    }
                    parentId != null -> {
                        val parentView = finder(thisRef, parentId!!)
                                ?: throw RuntimeException("Unable to bind ${property.name}; could not find specified parent with id ${ContextKeeper.appContext.resources.getResourceEntryName(parentId!!)}")
                        parentView.findViewById(viewId)
                                ?: throw RuntimeException("Unable to bind ${property.name}; view not found in specified parent with id ${ContextKeeper.appContext.resources.getResourceEntryName(parentId!!)}")
                    }
                    else -> throw RuntimeException("Unable to bind ${property.name}; please provide parent view or specify parent view id")
                }
            } else {
                v = finder(thisRef, viewId) ?: throw RuntimeException("Unable to bind ${property.name}; findViewById returned null.")
            }
            cachedView = v as V
        }
        return cachedView!!
    }

    fun withParent(@IdRes parentId: Int): Binder<T, V> {
        useParent = true
        this.parentId = parentId
        return this
    }

    fun withParent(parentProvider: () -> View): Binder<T, V> {
        useParent = true
        this.parentProvider = parentProvider
        return this
    }

}

fun AttributeSet.obtainFor(view: View, styleableRes: IntArray, onAttribute: (a: TypedArray, index: Int) -> Unit) {
    val a: TypedArray = view.context.obtainStyledAttributes(this, styleableRes)
    for (i in 0 until a.indexCount) onAttribute(a, a.getIndex(i))
    a.recycle()
}

/**
 * Show the keyboard
 */
fun View.showKeyboard() {
    val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

/**
 * Hide the keyboard
 */
fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

/** Provides a view-binding delegate inside classes extending [Dialog]. See [Binder] for more information. */
inline fun <reified V : View> Dialog.bind(@IdRes id: Int): Binder<Dialog, V> = Binder(id) { dialog, viewId -> dialog.findViewById<V>(viewId) }

/** Provides a view-binding delegate inside classes extending [ViewGroup]. See [Binder] for more information. */
inline fun <reified V : View> ViewGroup.bind(@IdRes id: Int): Binder<ViewGroup, V> = Binder(id) { viewGroup, viewId -> viewGroup.findViewById<V>(viewId) }

/** Provides a view-binding delegate inside classes extending [Activity]. See [Binder] for more information. */
inline fun <reified V : View> Activity.bind(@IdRes id: Int): Binder<Activity, V> = Binder(id) { activity, viewId -> activity.findViewById<V>(viewId) }

/** Provides a view-binding delegate inside classes extending [Fragment]. See [Binder] for more information. */
fun <V : View> Fragment.bind(@IdRes id: Int): Binder<Fragment, V> = Binder(id) { it, viewId -> it.view?.findViewById(viewId) }

fun View.requestAccessibilityFocus(delay: Long = 500) {
    val a11yManager = (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager)
    if (a11yManager.isEnabled || a11yManager.isTouchExplorationEnabled) {
        isFocusable = true
        isFocusableInTouchMode = true
        postDelayed({ requestFocus(); sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED) }, delay)
    }
}

/**
 * Loads the given resource as this Toolbar's icon, assigns it the given content description, and
 * propagates its clicks to the provided function.
 */
@JvmName("setupToolbarNavButtonWithCallback")
fun Toolbar?.setupAsNavButtonWithCallback(
    @DrawableRes iconResId: Int,
    @StringRes contentDescriptionResId: Int,
    onClick: () -> Unit) {
    if (this == null) return
    setNavigationIcon(iconResId)
    setNavigationContentDescription(contentDescriptionResId)
    setNavigationOnClickListener { onClick() }
    requestAccessibilityFocus()
}

/**
 * Loads the given resource as this Toolbar's icon, assigns it the given content description, and
 * propagates its clicks to the provided function.
 */
@JvmName("setupToolbarNavButtonWithoutCallback")
fun Toolbar?.setupAsNavButtonIcon(
        @DrawableRes iconResId: Int,
        @StringRes contentDescriptionResId: Int) {
    if (this == null) return
    setNavigationIcon(iconResId)
    setNavigationContentDescription(contentDescriptionResId)
    requestAccessibilityFocus()
}

/**
 * Changes this Toolbar's icon to a back arrow. Click events on this icon are propagated to the
 * provided function
 */
@SuppressLint("PrivateResource")
@JvmName("setupToolbarBackButton")
fun Toolbar?.setupAsBackButton(onClick: () -> Unit) = setupAsNavButtonWithCallback(
        R.drawable.abc_ic_ab_back_material,
        R.string.abc_action_bar_up_description,
        onClick
)

/**
 * Changes this Toolbar's icon to a back arrow. Click events will attempt to call onBackPressed()
 * on the given fragment's activity
 */
@JvmName("setupToolbarBackButton")
fun Toolbar?.setupAsBackButton(fragment: Fragment?) = setupAsBackButton {
    fragment?.activity?.onBackPressed()
}

/**
 * Changes this Toolbar's icon to a back arrow. Click events will attempt to call onBackPressed()
 * on the given fragment's activity
 */
@JvmName("setupToolbarBackButtonAsBackPressedOnly")
fun Toolbar?.setupAsBackButtonAsBackPressedOnly(fragment: Fragment?) = setupAsBackButton {
    fragment?.activity?.onBackPressed()
}

/**
 * Changes this Toolbar's icon to a close (X) icon. Click events on this icon are propagated to the
 * provided function.
 */
//@SuppressLint("PrivateResource")
//@JvmName("setupToolbarCloseButton")
//fun Toolbar?.setupAsCloseButton(onClick: () -> Unit) = setupAsNavButtonWithCallback(
//        android.support.v7.appcompat.R.drawable.abc_ic_clear_material, R.string.action_close,
//        onClick
//)


/**
 * Changes this Toolbar's icon to a close (X) icon. Click events will attempt to call onBackPressed()
 * on the given fragment's activity
 */
//@JvmName("setupToolbarCloseButton")
//fun Toolbar?.setupAsCloseButton(fragment: Fragment?) = setupAsCloseButton { fragment?.activity?.onBackPressed() }

/**
 * Inflates the provided menu resource into this Toolbar and propagates menu item click events
 * to the provided callback
 *
 * Note: This clears any existing menu items. It is safe to call multiple times throughout
 * the Activity/Fragment lifecycle, but should probably not be used when it is expected that
 * the menu will also be populated by other sources.
 */
@JvmName("setupToolbarMenu")
fun Toolbar?.setMenu(@MenuRes menuResId: Int, callback: (MenuItem) -> Unit) {
    if (this == null) return
    menu.clear()
    inflateMenu(menuResId)
    setOnMenuItemClickListener { callback(it); true }
}

fun RecyclerView.removeAllItemDecorations() {
    generateSequence(0) { it + 1 }
            .map { getItemDecorationAt(it) }
            .takeWhile { it != null }
            .toList()
            .forEach { removeItemDecoration(it) }
}