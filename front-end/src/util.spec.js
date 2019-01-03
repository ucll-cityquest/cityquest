import { findIndexOr, range } from "./util";

describe("findIndexOr", () => {
  it("returns default value if not in array", () => {
    const array = [1, 2, 3];
    const toFind = 5;

    const expected = Symbol("DEFAULT_VALUE");

    expect(findIndexOr(array, i => i === toFind, expected)).toBe(expected);
  });

  it("returns the correct value when found", () => {
    const array = [1, 2, 3];
    const toFind = 2;

    const expected = 1;

    expect(findIndexOr(array, i => i === toFind, -1)).toBe(expected);
  });
});

describe("range", () => {
  it("It should be inclusive", () => {
    const actual = range(0, 5);
    const expected = [0, 1, 2, 3, 4, 5];

    expect(actual).toEqual(expected);
  });

  it("Should throw an error if step = 0", () => {
    expect(() => range(0, 5, 0)).toThrowError();
  });
});
