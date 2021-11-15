package exercises;

import ipp.Color;
import ipp.SnapApp;
import ipp.Sprite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort extends SnapApp {

  private List<Integer> list = new ArrayList<Integer>();
  private int size = 50;
  private Sprite drawer;

  @Override
  public void start() {
    drawer = addSprite("drawer");
    List<Integer> input = generateScramble(size);
    list.addAll(input);
    System.out.println(list);
    for (int i = 0; i < list.size(); i++) {
      drawBar(i, size, list.get(i));
    }
    // addThread(() -> quicksort(0, size - 1));
    addThread(
        () -> {
          heapsort();
        });
  }

  public List<Integer> generateScramble(int len) {
    List<Integer> scrambleList = new ArrayList<Integer>();
    for (int i = 1; i <= len; i++) {
      scrambleList.add(i);
    }
    Collections.shuffle(scrambleList);
    return scrambleList;
  }

  public void drawBar(int index, int maxVal, int val) {
    double barX = ((double) index / (maxVal - 1) * 576.0) - 288.0;
    double barHeight = ((double) val / maxVal * 440.0);
    drawer.setPenSize((596.0 / maxVal) * 0.95);
    drawer.moveTo(barX, 225.0);
    drawer.setPenColor(Color.WHITE);
    drawer.penDown();
    drawer.moveTo(barX, -225.0);
    drawer.setPenSize((596.0 / maxVal) * 0.8);
    drawer.setPenColor(Color.BLACK);
    drawer.moveTo(barX, -225.0 + barHeight);
    drawer.penUp();
  }

  public void swap(int i1, int i2) {
    int x = list.get(i1);
    list.set(i1, list.get(i2));
    list.set(i2, x);
    drawBar(i1, size, list.get(i1));
    drawBar(i2, size, list.get(i2));
    drawer.wait(0.01);
  }

  public void bubble() {
    boolean swapped = true;
    while (swapped) {
      swapped = false;
      for (int i = 0; i < list.size() - 1; i++) {
        if (list.get(i) > list.get(i + 1)) {
          swap(i, i + 1);
          swapped = true;
        }
      }
    }
  }

  public void quicksort(int startIndex, int endIndex) {
    if (endIndex - startIndex <= 1) {
      if (endIndex < startIndex) {
        return;
      }
      if (list.get(endIndex) < list.get(startIndex)) {
        swap(startIndex, endIndex);
      }
      return;
    }
    int pivot = list.get(startIndex);
    int low = startIndex + 1;
    int high = endIndex;
    outerloop:
    while (true) {
      while (list.get(high) >= pivot) {
        high--;
        if (high < low) {
          break outerloop;
        }
      }
      while (list.get(low) < pivot) {
        low++;
        if (low >= high) {
          break outerloop;
        }
      }
      swap(low, high);
    }
    swap(startIndex, high);
    quicksort(startIndex, high - 1);
    quicksort(high + 1, endIndex);
  }

  public void heapsort() {
    buildHeap();
    for (int i = list.size() - 1; i > 0; i--) {
      swap(0, i);
      heapify(0, i);
    }
  }

  public void buildHeap() {
    for (int i = (list.size() / 2) - 1; i >= 0; i--) {
      heapify(i, list.size());
    }
  }

  public void heapify(int index, int heapSize) {
    int largest = index;
    int left = index * 2;
    int right = left + 1;
    if ((left <= heapSize - 1) && list.get(left) > list.get(largest)) {
      largest = left;
    }
    if ((right <= heapSize - 1) && list.get(right) > list.get(largest)) {
      largest = right;
    }
    if (largest != index) {
      swap(index, largest);
      heapify(largest, heapSize);
    }
    return;
  }
}
