--CREATE TABLE employees (
--  employee_id SERIAL PRIMARY KEY,
--  first_name VARCHAR(50) NOT NULL,
--  last_name VARCHAR(50) NOT NULL
--);

CREATE TABLE tasks (
  task_id SERIAL PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description TEXT,
  status VARCHAR(20) NOT NULL DEFAULT 'NEW' 
    CHECK (status IN ('NEW', 'IN_PROGRESS', 'DONE'))
);

CREATE TABLE time_records (
  time_record_id SERIAL PRIMARY KEY, 
  employee_id INT NOT NULL,
  task_id INT NOT NULL,
  begin_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  work_description TEXT,
 -- CONSTRAINT fk_time_records_employees
 --   FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
  CONSTRAINT fk_time_records_tasks
    FOREIGN KEY (task_id) REFERENCES tasks(task_id)
);
