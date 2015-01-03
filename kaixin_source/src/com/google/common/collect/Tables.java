package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class Tables
{
  public static <R, C, V> Table.Cell<R, C, V> immutableCell(@Nullable R paramR, @Nullable C paramC, @Nullable V paramV)
  {
    return new ImmutableCell(paramR, paramC, paramV);
  }

  public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> paramMap, Supplier<? extends Map<C, V>> paramSupplier)
  {
    Preconditions.checkArgument(paramMap.isEmpty());
    Preconditions.checkNotNull(paramSupplier);
    return new StandardTable(paramMap, paramSupplier);
  }

  public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> paramTable, Function<? super V1, V2> paramFunction)
  {
    return new TransformedTable(paramTable, paramFunction);
  }

  public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> paramTable)
  {
    if ((paramTable instanceof TransposeTable))
      return ((TransposeTable)paramTable).original;
    return new TransposeTable(paramTable);
  }

  static abstract class AbstractCell<R, C, V>
    implements Table.Cell<R, C, V>
  {
    public boolean equals(Object paramObject)
    {
      if (paramObject == this);
      while (true)
      {
        return true;
        if (!(paramObject instanceof Table.Cell))
          break;
        Table.Cell localCell = (Table.Cell)paramObject;
        if ((!Objects.equal(getRowKey(), localCell.getRowKey())) || (!Objects.equal(getColumnKey(), localCell.getColumnKey())) || (!Objects.equal(getValue(), localCell.getValue())))
          return false;
      }
      return false;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = getRowKey();
      arrayOfObject[1] = getColumnKey();
      arrayOfObject[2] = getValue();
      return Objects.hashCode(arrayOfObject);
    }

    public String toString()
    {
      return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
    }
  }

  static final class ImmutableCell<R, C, V> extends Tables.AbstractCell<R, C, V>
    implements Serializable
  {
    private static final long serialVersionUID;
    private final C columnKey;
    private final R rowKey;
    private final V value;

    ImmutableCell(@Nullable R paramR, @Nullable C paramC, @Nullable V paramV)
    {
      this.rowKey = paramR;
      this.columnKey = paramC;
      this.value = paramV;
    }

    public C getColumnKey()
    {
      return this.columnKey;
    }

    public R getRowKey()
    {
      return this.rowKey;
    }

    public V getValue()
    {
      return this.value;
    }
  }

  private static class TransformedTable<R, C, V1, V2>
    implements Table<R, C, V2>
  {
    TransformedTable<R, C, V1, V2>.CellSet cellSet;
    Map<C, Map<R, V2>> columnMap;
    final Table<R, C, V1> fromTable;
    final Function<? super V1, V2> function;
    Map<R, Map<C, V2>> rowMap;
    Collection<V2> values;

    TransformedTable(Table<R, C, V1> paramTable, Function<? super V1, V2> paramFunction)
    {
      this.fromTable = ((Table)Preconditions.checkNotNull(paramTable));
      this.function = ((Function)Preconditions.checkNotNull(paramFunction));
    }

    Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction()
    {
      return new Function()
      {
        public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> paramCell)
        {
          return Tables.immutableCell(paramCell.getRowKey(), paramCell.getColumnKey(), Tables.TransformedTable.this.function.apply(paramCell.getValue()));
        }
      };
    }

    public Set<Table.Cell<R, C, V2>> cellSet()
    {
      if (this.cellSet == null)
      {
        CellSet localCellSet = new CellSet();
        this.cellSet = localCellSet;
        return localCellSet;
      }
      return this.cellSet;
    }

    public void clear()
    {
      this.fromTable.clear();
    }

    public Map<R, V2> column(C paramC)
    {
      return Maps.transformValues(this.fromTable.column(paramC), this.function);
    }

    public Set<C> columnKeySet()
    {
      return this.fromTable.columnKeySet();
    }

    public Map<C, Map<R, V2>> columnMap()
    {
      if (this.columnMap == null)
      {
        Map localMap = createColumnMap();
        this.columnMap = localMap;
        return localMap;
      }
      return this.columnMap;
    }

    public boolean contains(Object paramObject1, Object paramObject2)
    {
      return this.fromTable.contains(paramObject1, paramObject2);
    }

    public boolean containsColumn(Object paramObject)
    {
      return this.fromTable.containsColumn(paramObject);
    }

    public boolean containsRow(Object paramObject)
    {
      return this.fromTable.containsRow(paramObject);
    }

    public boolean containsValue(Object paramObject)
    {
      return values().contains(paramObject);
    }

    Map<C, Map<R, V2>> createColumnMap()
    {
      3 local3 = new Function()
      {
        public Map<R, V2> apply(Map<R, V1> paramMap)
        {
          return Maps.transformValues(paramMap, Tables.TransformedTable.this.function);
        }
      };
      return Maps.transformValues(this.fromTable.columnMap(), local3);
    }

    Map<R, Map<C, V2>> createRowMap()
    {
      2 local2 = new Function()
      {
        public Map<C, V2> apply(Map<C, V1> paramMap)
        {
          return Maps.transformValues(paramMap, Tables.TransformedTable.this.function);
        }
      };
      return Maps.transformValues(this.fromTable.rowMap(), local2);
    }

    public boolean equals(@Nullable Object paramObject)
    {
      if (paramObject == this)
        return true;
      if ((paramObject instanceof Table))
      {
        Table localTable = (Table)paramObject;
        return cellSet().equals(localTable.cellSet());
      }
      return false;
    }

    public V2 get(Object paramObject1, Object paramObject2)
    {
      if (contains(paramObject1, paramObject2))
        return this.function.apply(this.fromTable.get(paramObject1, paramObject2));
      return null;
    }

    public int hashCode()
    {
      return cellSet().hashCode();
    }

    public boolean isEmpty()
    {
      return this.fromTable.isEmpty();
    }

    public V2 put(R paramR, C paramC, V2 paramV2)
    {
      throw new UnsupportedOperationException();
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V2> paramTable)
    {
      throw new UnsupportedOperationException();
    }

    public V2 remove(Object paramObject1, Object paramObject2)
    {
      if (contains(paramObject1, paramObject2))
        return this.function.apply(this.fromTable.remove(paramObject1, paramObject2));
      return null;
    }

    public Map<C, V2> row(R paramR)
    {
      return Maps.transformValues(this.fromTable.row(paramR), this.function);
    }

    public Set<R> rowKeySet()
    {
      return this.fromTable.rowKeySet();
    }

    public Map<R, Map<C, V2>> rowMap()
    {
      if (this.rowMap == null)
      {
        Map localMap = createRowMap();
        this.rowMap = localMap;
        return localMap;
      }
      return this.rowMap;
    }

    public int size()
    {
      return this.fromTable.size();
    }

    public String toString()
    {
      return rowMap().toString();
    }

    public Collection<V2> values()
    {
      if (this.values == null)
      {
        Collection localCollection = Collections2.transform(this.fromTable.values(), this.function);
        this.values = localCollection;
        return localCollection;
      }
      return this.values;
    }

    class CellSet extends Collections2.TransformedCollection<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>
      implements Set<Table.Cell<R, C, V2>>
    {
      CellSet()
      {
        super(Tables.TransformedTable.this.cellFunction());
      }

      public boolean contains(Object paramObject)
      {
        Table.Cell localCell;
        if ((paramObject instanceof Table.Cell))
        {
          localCell = (Table.Cell)paramObject;
          if (Objects.equal(localCell.getValue(), Tables.TransformedTable.this.get(localCell.getRowKey(), localCell.getColumnKey())))
            break label45;
        }
        label45: 
        do
          return false;
        while ((localCell.getValue() == null) && (!Tables.TransformedTable.this.fromTable.contains(localCell.getRowKey(), localCell.getColumnKey())));
        return true;
      }

      public boolean equals(Object paramObject)
      {
        return Sets.equalsImpl(this, paramObject);
      }

      public int hashCode()
      {
        return Sets.hashCodeImpl(this);
      }

      public boolean remove(Object paramObject)
      {
        if (contains(paramObject))
        {
          Table.Cell localCell = (Table.Cell)paramObject;
          Tables.TransformedTable.this.fromTable.remove(localCell.getRowKey(), localCell.getColumnKey());
          return true;
        }
        return false;
      }
    }
  }

  private static class TransposeTable<C, R, V>
    implements Table<C, R, V>
  {
    private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function()
    {
      public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> paramCell)
      {
        return Tables.immutableCell(paramCell.getColumnKey(), paramCell.getRowKey(), paramCell.getValue());
      }
    };
    TransposeTable<C, R, V>.CellSet cellSet;
    final Table<R, C, V> original;

    TransposeTable(Table<R, C, V> paramTable)
    {
      this.original = ((Table)Preconditions.checkNotNull(paramTable));
    }

    public Set<Table.Cell<C, R, V>> cellSet()
    {
      CellSet localCellSet = this.cellSet;
      if (localCellSet == null)
      {
        localCellSet = new CellSet();
        this.cellSet = localCellSet;
      }
      return localCellSet;
    }

    public void clear()
    {
      this.original.clear();
    }

    public Map<C, V> column(R paramR)
    {
      return this.original.row(paramR);
    }

    public Set<R> columnKeySet()
    {
      return this.original.rowKeySet();
    }

    public Map<R, Map<C, V>> columnMap()
    {
      return this.original.rowMap();
    }

    public boolean contains(@Nullable Object paramObject1, @Nullable Object paramObject2)
    {
      return this.original.contains(paramObject2, paramObject1);
    }

    public boolean containsColumn(@Nullable Object paramObject)
    {
      return this.original.containsRow(paramObject);
    }

    public boolean containsRow(@Nullable Object paramObject)
    {
      return this.original.containsColumn(paramObject);
    }

    public boolean containsValue(@Nullable Object paramObject)
    {
      return this.original.containsValue(paramObject);
    }

    public boolean equals(@Nullable Object paramObject)
    {
      if (paramObject == this)
        return true;
      if ((paramObject instanceof Table))
      {
        Table localTable = (Table)paramObject;
        return cellSet().equals(localTable.cellSet());
      }
      return false;
    }

    public V get(@Nullable Object paramObject1, @Nullable Object paramObject2)
    {
      return this.original.get(paramObject2, paramObject1);
    }

    public int hashCode()
    {
      return cellSet().hashCode();
    }

    public boolean isEmpty()
    {
      return this.original.isEmpty();
    }

    public V put(C paramC, R paramR, V paramV)
    {
      return this.original.put(paramR, paramC, paramV);
    }

    public void putAll(Table<? extends C, ? extends R, ? extends V> paramTable)
    {
      this.original.putAll(Tables.transpose(paramTable));
    }

    public V remove(@Nullable Object paramObject1, @Nullable Object paramObject2)
    {
      return this.original.remove(paramObject2, paramObject1);
    }

    public Map<R, V> row(C paramC)
    {
      return this.original.column(paramC);
    }

    public Set<C> rowKeySet()
    {
      return this.original.columnKeySet();
    }

    public Map<C, Map<R, V>> rowMap()
    {
      return this.original.columnMap();
    }

    public int size()
    {
      return this.original.size();
    }

    public String toString()
    {
      return rowMap().toString();
    }

    public Collection<V> values()
    {
      return this.original.values();
    }

    class CellSet extends Collections2.TransformedCollection<Table.Cell<R, C, V>, Table.Cell<C, R, V>>
      implements Set<Table.Cell<C, R, V>>
    {
      CellSet()
      {
        super(Tables.TransposeTable.TRANSPOSE_CELL);
      }

      public boolean contains(Object paramObject)
      {
        if ((paramObject instanceof Table.Cell))
        {
          Table.Cell localCell = (Table.Cell)paramObject;
          return Tables.TransposeTable.this.original.cellSet().contains(Tables.immutableCell(localCell.getColumnKey(), localCell.getRowKey(), localCell.getValue()));
        }
        return false;
      }

      public boolean equals(Object paramObject)
      {
        int i;
        if (paramObject == this)
          i = 1;
        Set localSet;
        int j;
        int k;
        do
        {
          boolean bool;
          do
          {
            return i;
            bool = paramObject instanceof Set;
            i = 0;
          }
          while (!bool);
          localSet = (Set)paramObject;
          j = localSet.size();
          k = size();
          i = 0;
        }
        while (j != k);
        return containsAll(localSet);
      }

      public int hashCode()
      {
        return Sets.hashCodeImpl(this);
      }

      public boolean remove(Object paramObject)
      {
        if ((paramObject instanceof Table.Cell))
        {
          Table.Cell localCell = (Table.Cell)paramObject;
          return Tables.TransposeTable.this.original.cellSet().remove(Tables.immutableCell(localCell.getColumnKey(), localCell.getRowKey(), localCell.getValue()));
        }
        return false;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.Tables
 * JD-Core Version:    0.6.0
 */