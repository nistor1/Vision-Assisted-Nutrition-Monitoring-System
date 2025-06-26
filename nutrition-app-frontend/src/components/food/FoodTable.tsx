import React from 'react';
import { Table, Button, Space, Tag, Image } from 'antd';
import type { FoodItemSimple } from '../../types/FoodEntities';
import type { TablePaginationConfig } from 'antd/es/table';
import { useAuth } from '../../context/AuthContext';

interface FoodsTableProps {
  data: FoodItemSimple[];
  onView: (id: string) => void;
  onEdit: (id: string) => void;
  onDelete: (id: string) => void;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  onChange: (pagination: TablePaginationConfig) => void;
}

const FoodsTable: React.FC<FoodsTableProps> = ({ data, onView, onEdit, onDelete, pagination, onChange}) => {
  const { user } = useAuth();
  const isAdmin = user.role === 'ADMIN';
  const columns = [
    {
      title: 'Image',
      dataIndex: 'imageUrl',
      key: 'imageUrl',
      render: (url: string) => <Image src={url} alt="Food" width={40} height={40} />,
    },
    {
      title: 'Name',
      dataIndex: 'foodName',
      key: 'foodName',
    },
    {
      title: 'Category',
      dataIndex: 'category',
      key: 'category',
    },
    {
      title: 'Serving Size',
      dataIndex: 'servingSize',
      key: 'servingSize',
      render: (size: number | null, record: FoodItemSimple) =>
        size !== null ? `${size} ${record.servingUnit}` : 'N/A',
    },
    {
      title: 'Status',
      dataIndex: 'active',
      key: 'active',
      render: (active: boolean) =>
        <Tag color={active ? 'green' : 'red'}>{active ? 'Active' : 'Inactive'}</Tag>,
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 250,
      render: (_: unknown, record: FoodItemSimple) => {
        const actions = [
          <Button size="small" onClick={() => onView(record.id)} key="view">View</Button>,
        ];

        if (isAdmin) {
          actions.push(
            <Button size="small" type="primary" onClick={() => onEdit(record.id)} key="edit">Edit</Button>,
            <Button size="small" danger onClick={() => onDelete(record.id)} key="delete">Delete</Button>
          );
        }

        return <Space>{actions}</Space>;
      }
    }
  ];

  return (
    <Table
      dataSource={data}
      columns={columns}
      rowKey="id"
      pagination={{
        current: pagination.current,
        pageSize: pagination.pageSize,
        total: pagination.total,
        showSizeChanger: true,
        pageSizeOptions: ['5', '10', '20', '30'],
      }}
      onChange={(pagination) => onChange(pagination)}
    />
  );
};

export default FoodsTable;
