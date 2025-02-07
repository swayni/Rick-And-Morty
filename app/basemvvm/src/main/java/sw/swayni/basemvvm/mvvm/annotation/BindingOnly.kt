package sw.swayni.basemvvm.mvvm.annotation


/**
 * Specifies that this annotation should be used to mark binding properties and functions.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@DslMarker
@Retention(AnnotationRetention.BINARY)
annotation class BindingOnly