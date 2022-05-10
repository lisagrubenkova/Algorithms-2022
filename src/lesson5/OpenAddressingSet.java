package lesson5;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private boolean deleted[];

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
        deleted = new boolean[capacity];
        for (int i = 0; i < capacity - 1; i++) {
            deleted[i] = false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o) && deleted[index] == false) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null && deleted[index] == false) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        if (deleted[index] == true) deleted[index] = false;
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    // Трудоёмкость: O(n)
    // Ресурсоёмкость: O(1)
    @Override
    public boolean remove(Object o) {
        int startingIndex = startingIndex(o);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o) && deleted[index] == false) {
                deleted[index] = true;
                size--;
                return true;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                break;
            }
            current = storage[index];
        }
        return false;
    }

    /**
     * Создание итератора для обхода таблицы
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private int currentNumber = 0;

            private int getNextIndex() {
                int nextIndex = index;
                while (storage[nextIndex] == null || deleted[nextIndex] == true) {
                    nextIndex++;
                }

                return (nextIndex - index);
            }

            // Трудоёмкость: O(1)
            // Ресурсоёмкость: O(1)
            @Override
            public boolean hasNext() {
                return (currentNumber < size);
            }

            // Трудоёмкость: O(n)
            // Ресурсоёмкость: O(1)
            @Override
            public T next() throws NullPointerException {
                if (!this.hasNext()) throw new NoSuchElementException();

                index += getNextIndex();
                T current = (T) storage[index];

                currentNumber++;
                index++;
                return current;
            }
        };
    }
}