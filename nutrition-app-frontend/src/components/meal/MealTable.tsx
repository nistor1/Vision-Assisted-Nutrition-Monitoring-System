import React from 'react';
import { Table, Button, Space, Tag, Tooltip } from 'antd';
import type { TablePaginationConfig } from 'antd/es/table';
import type { Meal } from '../../types/MealEntities';
import dayjs from 'dayjs';

interface MealTableProps {
  data: Meal[];
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

const MealTable: React.FC<MealTableProps> = ({
                                               data,
                                               onView,
                                               onEdit,
                                               onDelete,
                                               pagination,
                                               onChange
                                             }) => {
  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Type',
      dataIndex: 'mealType',
      key: 'mealType',
      render: (type: string) => <Tag color="blue">{type}</Tag>,
    },
    {
      title: 'Status',
      dataIndex: 'mealStatus',
      key: 'mealStatus',
      render: (status: string) => (
        <Tag color={status === 'FINALIZED' ? 'green' : 'orange'}>{status}</Tag>
      ),
    },
    {
      title: 'Date',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date: string) => dayjs(date).format('YYYY-MM-DD HH:mm'),
    },
    {
      title: 'Calories',
      dataIndex: 'totalCalories',
      key: 'totalCalories',
      render: (value: number) => value?.toFixed(2) ?? '-',
    },
    {
      title: 'Proteins',
      dataIndex: 'totalProteins',
      key: 'totalProteins',
      render: (value: number) => value?.toFixed(2) ?? '-',
    },
    {
      title: 'Carbs',
      dataIndex: 'totalCarbohydrates',
      key: 'totalCarbohydrates',
      render: (value: number) => value?.toFixed(2) ?? '-',
    },
    {
      title: 'Fats',
      dataIndex: 'totalFats',
      key: 'totalFats',
      render: (value: number) => value?.toFixed(2) ?? '-',
    },
    {
      title: 'Sugars',
      dataIndex: 'totalSugars',
      key: 'totalSugars',
      render: (value: number) => value?.toFixed(2) ?? '-',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: 250,
      render: (_: unknown, record: Meal) => {
        const actions = [
          <Tooltip title="View" key="view">
            <Button size="small" onClick={() => onView(record.id)}>View</Button>
          </Tooltip>,
          record.mealStatus !== 'FINALIZED' && (
            <Tooltip title="Edit" key="edit">
              <Button size="small" type="primary" onClick={() => onEdit(record.id)}>Confirm</Button>
            </Tooltip>
          ),
          <Tooltip title="Delete" key="delete">
            <Button size="small" danger onClick={() => onDelete(record.id)}>Delete</Button>
          </Tooltip>
        ];
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

export default MealTable;
