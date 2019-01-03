/**
 * Create a stream with the current location.
 *
 * @param {function} succesCb format: ([latitude, longitude] => any)
 * @param {function} errorCb format: (error => any), only called when there is an error when watching
 *                                   not when creating.
 * @throws {Error} When geolocation is not supported
 * @returns close function
 */
export function createLocationStream(
  successCb,
  // eslint-disable-next-line no-console
  errorCb = err => console.error(err)
) {
  if (navigator.geolocation === undefined) {
    throw new Error("Browser does not support geolocation");
  }

  const options = {
    enableHighAccuracy: false,
    timeout: 10000,
    maximumAge: 0
  };

  const id = navigator.geolocation.watchPosition(
    position =>
      successCb([position.coords.latitude, position.coords.longitude]),
    errorCb,
    options
  );

  return () => navigator.geolocation.clearWatch(id);
}
