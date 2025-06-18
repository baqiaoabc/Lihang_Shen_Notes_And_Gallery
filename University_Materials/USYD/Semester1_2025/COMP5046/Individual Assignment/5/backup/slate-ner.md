### Named Entity Recognition Labelling Example

This is a tutorial about text labelling for Named Entity Recognition with
slate. It is intended to be read and followed from within slate. When you see a
line with '>-' you should try out the command.

Assuming you have installed slate with pip, run it like so:

```shell
slate slate-ner.md -t categorical -s token -o -c ner.config -l log.tutorial.ner.txt -sl -sm
```

This command is also an example of how to run the code. For an explanation of
all the parts, see the bottom of this file. For the full range of command line
arguments, see https://jkk.name/slate/

When labelling, the current item is underlined. In this case, that means the
very first token of this document.  There are two ways to move the underline:

  >- Use the arrow keys to move left, up, down, and right
  >- Or use `j`, `i`, `o`, and `;` to move left, up, down and right,
     respectively

To select multiple tokens, start at the leftmost token, then:

  >- Hold `SHIFT` while moving to the right to expand your selection
  >- Hold `SHIFT` while moving to the left to contract your selection

To move, expand and contract faster:

  >- Type a number before any of the above commands and it will be repeated
     that many times

The command line arguments used for this tutorial also make it so that at the
bottom of the screen you can see:

 - A horizontal line
 - One or more lines listing the different labels, the colour they appear as,
   and the keys to apply them
 - One or more lines listing what labels are applied to the text that is
   currently selected.

To label an item, first move and expand to select the relevant text, then:

  >- Type `SPACE` and then `o` to apply the 'ORG' label

Note that the text has changed colour to indicate that it is labeled and the
label is disaplayed at the bottom of the screen.

  >- Type `SPACE` and then `o` again to remove the label
  >- Similarly, type `SPACE` and then `p` to apply the 'PERSON' label

Note that various colours are used for the labels, but some repeat (future
versions of this code may use a wider range of colours).

It is possible to apply multiple labels to the same text, or have overlapping
labels. To see this in action:

  >- Type `SPACE` and then `d` to also apply the 'DATE' label

Note that at the bottom there are now multiple annotations shown. Also, the
text is in a cyan colour to indicate that it has multiple labels.

Now let's remove the annotations:

  >- Type `u` to remove both labels on this item

For more commands please see the README.md file. For now, you can:

 >- Type `q` to save and quit

#### Command Explanation

The command for this tutorial is:

```shell
slate slate-ner.md -t categorical -s token -o -c ner.config -l log.tutorial.ner.txt -sl -sm
```

It says to:

 - run the program (`slate`),
 - annotating this file (`slate-ner.md`),
 - with categories (`-t categorical`),
 - applied to tokens (`-s token`),
 - reading and overwriting any existing annotation file (`-o`),
 - with a special configuration file (`-c ner.config`),
 - logging to a specified file (`-l log.tutorial.ner.txt`),
 - showing the set of possible labels (`-sl`),
 - and showing the labels under the cursor (`-sm`)
