package lt.tiem625.docbuild.data;

import lt.tiem625.docbuild.ViewableEntity;

/**
 * <p>
 *  Marker interface for a container entity that holds flow services and structures
 * </p>
 * <p>
 *  This can be one of two distinct things - a "shadow" data asset or a registered application
 * </p>
 */
public sealed interface BusinessApplication extends ViewableEntity permits DataAsset, Application {
}
