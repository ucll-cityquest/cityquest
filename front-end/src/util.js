import uuid from "uuid/v1";

export const getUserId = () => localStorage.getItem("userId");

export const setUserId = id => {
  localStorage.setItem("userId", id);
};

export const initUserId = () => {
  const id = getUserId();
  if (!id) {
    setUserId(uuid());
  }
};

/**
 * Finds index of an array or a default value
 * @param {array} array
 * @param {(element, index, array) => boolean} predicate
 * @param {any} defaultValue
 */
export function findIndexOr(array, predicate, defaultValue = -1) {
  const value = array.findIndex(predicate);
  if (value === -1) return defaultValue;
  return value;
}

export function range(min, max, step = 1) {
  if (step === 0) {
    throw new Error("Step cannot be 0");
  }

  const array = [];
  for (let i = min; i <= max; i += step) {
    array.push(i);
  }

  return array;
}

export function toRadian(degree) {
  return (degree * Math.PI) / 180;
}
