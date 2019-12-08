# Advent of Code 2019

Advent of code: https://adventofcode.com/2019

Don't know what to expect? Check out the previous years: [2018](https://adventofcode.com/2018), [2017](https://adventofcode.com/2017), [2016](https://adventofcode.com/2016), [2015](https://adventofcode.com/2015).

#### How does it work

- New puzzles are added each day at midnight EST (that is 06:00 CET)
- Solve the puzzle and post the solution to earn a star :star:,​ two stars can be earned each day
- The quicker you are the more points you get
- Document and share your frustrations, eureka's and black magic in #AoC2019

#### How to join

- Create an account / login: https://adventofcode.com/2019/auth/login
- Fork this repository and start hacking

#### Joining the leaderboard

- Join the Kunlabora leaderbord [on this link](https://adventofcode.com/2019/leaderboard/private) with this code: `239979-3c68168f`

#### Log

To improve knowledge sharing and experimentation, please document how to build and run your code. It can also be nice to keep a log here:

##### _30/11_

_Today I forked this repository in preparation of the start._

##### _01/12_

Today I learned about using `tailrec` as a function modifier on recursive functions so the program doesn't blow the stack and compiles it into an iteration function.

##### _04/12_

Got stuck on a simple for each.

Learned about using `by` as inheritance (see `Day2.IntCodes`)

Three flavors of updating a value in a List:

```kotlin
_intCodes.toMutableList()
    .apply {
        this[instruction.destinationAddress] = _intCodes[instruction.parameterAddress1] * _intCodes[instruction.parameterAddress2]
    }.toList()
```
Here's code with _intCodes as a MutableList:
```kotlin
_intCodes[opcodeStatement.destinationPosition] = _intCodes[opcodeStatement.position1] * _intCodes[opcodeStatement.position2]
```

Is ths first flavor worth it if _intCodes is already wrapped in a SmartCollection.

Another option would be to create an extension function on List: List.replace(index, newValue) to wrap `.toMutableList().apply{}.toList()` stuff.
As suggested by _ICHBINI_.

##### _08/12_

Refactored a bunch of stuff into the Common module:
* `Position`: to help with Position stuff like _Manhattan Distance_, ranging a `Position` from and until another `Position` on the same axis
* `Quadrants`: to organize Positions into quadrants
* `Metrics`: to measure how long stuff takes

I created an extension function for `Position` because I didn't want the common `Position` class to have to know about a `WireDirection` in which it was being pulled.

I also added an extension function to `Int` in Common and first put them in `Positioning`, but afterwards extracted them it into `IntExtensions` to make it a little bit more clear that I'm extending `Int` there.

`Int` was lacking a rangeTo that maintained order in both ways: from -1 to 1 and from 1 to -1. The former is indeed `rangeTo`, but the latter is `downTo` and returns a different type.

This is the umpteenth time that viewer _ICHBINI_ has helped me out quite a lot. I'm learning a lot from them and I'm really grateful they're watching my stream.
